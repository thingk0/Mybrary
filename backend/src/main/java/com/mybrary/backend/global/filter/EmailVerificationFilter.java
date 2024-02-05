package com.mybrary.backend.global.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Log4j2
@Component
public class EmailVerificationFilter extends OncePerRequestFilter {

    private final String SIGNUP_URL = "/api/v1/member";
    private final String POST_METHOD = "POST";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        if (!(request.getRequestURI().equals(SIGNUP_URL) && request.getMethod().equals(POST_METHOD))) {
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies == null || !isConfirmedEmailCookiePresent(cookies)) {
            log.warn("확인된 이메일 쿠키가 존재하지 않습니다. 회원가입을 위해서는 이메일 인증이 필요합니다.");
            sendAuthFailureResponse(response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isConfirmedEmailCookiePresent(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if ("confirmedEmail".equals(cookie.getName())) {
                return true;
            }
        }
        return false;
    }

    private static void sendAuthFailureResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String body = "{\n"
            + "  \"status\" : \"FAILED\",\n"
            + "  \"message\" : \"회원가입을 위해서는 이메일 인증이 필요합니다.\"\n"
            + "}";
        response.getWriter().write(body);
    }

}

