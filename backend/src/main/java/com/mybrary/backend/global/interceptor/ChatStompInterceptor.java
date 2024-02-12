package com.mybrary.backend.global.interceptor;

import com.mybrary.backend.global.jwt.provider.TokenProvider;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class ChatStompInterceptor implements ChannelInterceptor {

    private final TokenProvider tokenProvider;
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        // WebSocket 연결 시도 시 인증 토큰 검증
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            List<String> authorization = accessor.getNativeHeader("Authorization");

            // Authorization 헤더가 존재하며 Bearer 토큰이 포함된 경우
            if (authorization != null && !authorization.isEmpty() && authorization.get(0).startsWith(BEARER_PREFIX)) {
                String accessToken = getAccessToken(authorization);

                // 토큰 유효성 검증
                if (tokenProvider.validateToken(accessToken)) {
                    String email = tokenProvider.extractEmail(accessToken);

                    // 인증 정보를 Spring SecurityContext 에 설정
                    UsernamePasswordAuthenticationToken authentication = getSimpleAuthenticationToken(email);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    // 로깅: 인증 성공
                    log.info("event=Authentication-Success, email={}", email);
                } else {
                    // 로깅: 인증 실패
                    log.info("event=Authentication-Failure, token={}", accessToken);
                }
            } else {
                // 로깅: 인증 헤더 누락
                log.info("event=Authentication-Header-Missing, message=Authorization header is missing or does not contain Bearer token");
            }
        }
        return message;
    }

    // Authorization 헤더에서 액세스 토큰 추출
    private String getAccessToken(List<String> authorization) {
        return authorization.get(0).substring(BEARER_PREFIX.length());
    }

    // 간단한 사용자 인증 정보 생성
    private UsernamePasswordAuthenticationToken getSimpleAuthenticationToken(String email) {
        return new UsernamePasswordAuthenticationToken(email, null, Collections.singleton(new SimpleGrantedAuthority("USER")));
    }
}

