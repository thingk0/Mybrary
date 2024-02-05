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
public class TokenRefreshRequestFilter extends OncePerRequestFilter {

    private final FilterResponse filterResponse;
    final String TOKEN_URL = "/api/v1/member";
    final String PUT_METHOD = "PUT";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        if (!(request.getRequestURI().equals(TOKEN_URL) && request.getMethod().equals(PUT_METHOD))) {
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies == null || !isConfirmedRefreshTokenPresent(cookies)) {
            filterResponse.sendTokenReissueResponse(response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean isConfirmedRefreshTokenPresent(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if ("RefreshToken".equals(cookie.getName())) {
                return true;
            }
        }
        return false;
    }
}

