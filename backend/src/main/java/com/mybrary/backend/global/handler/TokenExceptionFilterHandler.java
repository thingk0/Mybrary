package com.mybrary.backend.global.handler;

import com.mybrary.backend.global.exception.jwt.UnauthorizedAccessException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class TokenExceptionFilterHandler extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (UnauthorizedAccessException e) {
            response.setStatus(401);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String body = "{\n"
                + "              \"status\" : \"FAILED\",\n"
                + "              \"message\" : \"로그인이 필요한 서비스입니다.\"\n"
                + "         }";
            response.getWriter().write(body);
        }
    }
}
