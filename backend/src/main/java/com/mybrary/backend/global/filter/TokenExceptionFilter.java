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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (UnauthorizedAccessException e) {
            log.error("TokenExceptionFilter : {} -> \nUnauthorizedAccessException : {}", request.getRequestURI(), e.getMessage(), e);
            filterResponse.sendLoginRequiredResponse(response);
        }
    }
}
