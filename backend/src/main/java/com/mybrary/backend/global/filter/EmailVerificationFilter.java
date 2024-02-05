package com.mybrary.backend.global.filter;

import com.mybrary.backend.global.format.code.FilterResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Log4j2
@Component
@RequiredArgsConstructor
public class EmailVerificationFilter extends OncePerRequestFilter {

    private final FilterResponse filterResponse;
    final String SIGNUP_URL = "/api/v1/member";
    final String POST_METHOD = "POST";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        if (!(request.getRequestURI().equals(SIGNUP_URL) && request.getMethod().equals(POST_METHOD))) {
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies == null || !isConfirmedEmailCookiePresent(cookies)) {
            log.warn("[이메일 인증 필터] 이메일 인증 << 쿠키 미발견 >>. 인증 실패 응답 전송: {}", request.getRequestURI());
            filterResponse.sendEmailAuthFailureResponse(response);
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
}


