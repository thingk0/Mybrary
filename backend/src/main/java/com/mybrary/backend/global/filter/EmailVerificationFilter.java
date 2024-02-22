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

    /**
     * 회원가입 요청에 대한 이메일 인증 쿠키 존재 여부를 검사하는 필터. 이메일 인증 쿠키가 없으면, 이메일 인증 실패 응답을 반환한다.
     *
     * @param request     HttpServletRequest 객체, 요청 정보를 담고 있음.
     * @param response    HttpServletResponse 객체, 응답을 구성하여 반환함.
     * @param filterChain FilterChain, 다음 필터로 요청을 전달하는 역할을 함.
     * @throws ServletException 요청 처리 중 발생할 수 있는 예외.
     * @throws IOException      입출력 처리 중 발생할 수 있는 예외.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        if (!(request.getRequestURI().equals(SIGNUP_URL) && request.getMethod().equals(POST_METHOD))) {
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        boolean emailCookiePresent = isConfirmedEmailCookiePresent(cookies);

        // 로깅: 이메일 인증 쿠키 존재 여부
        log.info("action=Email-Verification-Attempt, method={}, uri={}, email-Cookie-Present={}",
                 request.getMethod(), request.getRequestURI(), emailCookiePresent);

        if (!emailCookiePresent) {
            // 이메일 인증 실패
            log.warn("[Email Verification Filter] Missing email verification cookie. Sending failure response: {}", request.getRequestURI());
            filterResponse.sendEmailAuthFailureResponse(response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    /**
     * "confirmedEmail" 쿠키의 존재 여부를 확인한다.
     *
     * @param cookies HttpServletRequest에서 가져온 Cookie 배열
     * @return 이메일 인증 쿠키 존재 여부 (boolean)
     */
    private boolean isConfirmedEmailCookiePresent(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if ("confirmedEmail".equals(cookie.getName())) {
                return true;
            }
        }
        return false;
    }
}


