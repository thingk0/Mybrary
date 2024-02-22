package com.mybrary.backend.global.filter;

import com.mybrary.backend.global.exception.jwt.UnauthorizedAccessException;
import com.mybrary.backend.global.format.code.FilterResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
public class TokenExceptionFilter extends OncePerRequestFilter {

    private final FilterResponse filterResponse;

    /**
     * 요청 처리 중 발생하는 UnauthorizedAccessException을 캐치하고, 사용자에게 로그인이 필요함을 알리는 응답을 반환하는 필터.
     *
     * @param request     HttpServletRequest 객체, 요청 정보를 담고 있음.
     * @param response    HttpServletResponse 객체, 응답을 구성하여 반환함.
     * @param filterChain FilterChain, 다음 필터로 요청을 전달하는 역할을 함.
     * @throws ServletException 요청 처리 중 발생할 수 있는 예외.
     * @throws IOException      입출력 처리 중 발생할 수 있는 예외.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (UnauthorizedAccessException e) {
            log.error("TokenExceptionFilter : Unauthorized access attempt at [{}] -> \nException Message: {}",
                      request.getRequestURI(), e.getMessage(), e);
            // 응답 처리: 로그인이 필요함을 알리는 응답 반환
            filterResponse.sendLoginRequiredResponse(response);

        }
    }
}
