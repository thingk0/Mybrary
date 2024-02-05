package com.mybrary.backend.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriTemplate;

import java.util.Map;
import java.util.regex.Pattern;

@Component
public class NicknameValidInterceptor implements HandlerInterceptor {

    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,15}$");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String path = request.getRequestURI();
        UriTemplate template = new UriTemplate("/api/v1/member/nickname/{nickname}/exists");

        if (template.matches(path)) {
            Map<String, String> pathVariables = template.match(path);
            String nickname = pathVariables.get("nickname");
            if (nickname != null && !NICKNAME_PATTERN.matcher(nickname).matches()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid nickname format.");
                return false;
            }
        }

        return true;
    }
}
