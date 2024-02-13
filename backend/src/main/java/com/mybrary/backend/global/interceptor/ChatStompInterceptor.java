package com.mybrary.backend.global.interceptor;

import com.mybrary.backend.global.jwt.provider.TokenProvider;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

        log.info("message = {}", message);
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT == accessor.getCommand()) {
            String jwtToken = accessor.getFirstNativeHeader("Authorization");
            log.info("CONNECT authorizationHeader={}", jwtToken);

            if (jwtToken != null && jwtToken.startsWith(BEARER_PREFIX)) {
                String token = jwtToken.substring(BEARER_PREFIX.length());
                if (tokenProvider.validateToken(token)) {
                    String email = tokenProvider.extractEmail(token); // 예시 메소드, 실제 구현 필요

                    UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    accessor.setUser(authentication);
                    log.info("User authenticated email={}", email);
                }
            }
        }

        return message;
    }


}

