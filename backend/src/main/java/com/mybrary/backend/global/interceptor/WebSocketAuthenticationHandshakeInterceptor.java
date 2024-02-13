package com.mybrary.backend.global.interceptor;

import com.mybrary.backend.global.exception.jwt.AccessTokenNotFoundException;
import com.mybrary.backend.global.jwt.provider.TokenProvider;
import com.mybrary.backend.global.jwt.service.TokenService;
import com.mybrary.backend.global.util.StompPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Component
@RequiredArgsConstructor
public class WebSocketAuthenticationHandshakeInterceptor extends DefaultHandshakeHandler {

    private final TokenService tokenService;
    private final TokenProvider tokenProvider;

    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {

        String accessToken = tokenService.extractAccessToken((HttpServletRequest) request);
        if (accessToken == null) {
            throw new AccessTokenNotFoundException();
        }

        return new StompPrincipal(tokenProvider.extractEmail(accessToken));
    }

}