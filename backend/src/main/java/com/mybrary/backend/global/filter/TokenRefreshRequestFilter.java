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

    /**
     * Refresh 토큰 요청을 처리하기 위한 필터. 특정 URL (/api/v1/member)에 PUT 요청이 들어온 경우, Refresh 토큰의 존재 여부를 확인한다. Refresh 토큰이 존재하지 않으면, 적절한 응답을 반환한다.
     *
     * @param request     HttpServletRequest 객체
     * @param response    HttpServletResponse 객체
     * @param filterChain FilterChain 객체
     * @throws ServletException ServletException 예외
     * @throws IOException      IOException 예외
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        // 토큰 갱신 요청 확인
        if (!(request.getRequestURI().equals(TOKEN_URL) && request.getMethod().equals(PUT_METHOD))) {
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        boolean refreshTokenPresent = isConfirmedRefreshTokenPresent(cookies);

        log.info("action=TokenRefreshAttempt, method={}, uri={}, refreshTokenPresent={}",
                 request.getMethod(), request.getRequestURI(), refreshTokenPresent);

        // Refresh 토큰이 존재하지 않는 경우 응답 처리
        if (!refreshTokenPresent) {
            filterResponse.sendTokenReissueResponse(response);
            log.info("event=RefreshTokenMissing, action=BlockRequest");
            return;
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 쿠키 배열에서 "RefreshToken" 쿠키의 존재 여부를 확인한다.
     *
     * @param cookies HttpServletRequest에서 가져온 Cookie 배열
     * @return Refresh 토큰 존재 여부 (boolean)
     */
    private boolean isConfirmedRefreshTokenPresent(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if ("RefreshToken".equals(cookie.getName())) {
                return true;
            }
        }
        return false;
    }
}

