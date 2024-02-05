package com.mybrary.backend.global.jwt;

import com.mybrary.backend.global.util.CookieUtil;
import com.mybrary.backend.global.exception.jwt.RefreshTokenNotFoundException;
import com.mybrary.backend.global.exception.jwt.UnauthorizedAccessException;
import com.mybrary.backend.global.format.ErrorCode;
import com.mybrary.backend.global.jwt.repository.RefreshTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Log4j2
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final CookieUtil cookieUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AntPathMatcher pathMatcher;
    private final Map<String, Boolean> filterPaths;

    public JwtFilter(JwtProvider jwtProvider, CookieUtil cookieUtil,
                     RefreshTokenRepository refreshTokenRepository) {
        this.jwtProvider = jwtProvider;
        this.cookieUtil = cookieUtil;
        this.refreshTokenRepository = refreshTokenRepository;
        this.pathMatcher = new AntPathMatcher();
        this.filterPaths = initializeFilterPaths();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("URI: {}", request.getRequestURI());

        /* 토큰 필터 패스 */
        if (shouldFilter(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = extractTokenFromCookie(request);
        if (accessToken != null) {
            String email = jwtProvider.extractEmail(accessToken);
            if (!jwtProvider.verifyToken(accessToken)) {
                try {
                    reissue(email, response);
                } catch (RefreshTokenNotFoundException e) {
                    respondWithInvalidTokenError(response);
                }
            }
            setAuthentication(email, request);
            log.info("email: {}", email);
            filterChain.doFilter(request, response);
        } else {
            throw new UnauthorizedAccessException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
    }

    @Transactional
    public void reissue(String email, HttpServletResponse response) {
        refreshTokenRepository.findByValue(email).ifPresentOrElse(
            refreshToken -> {
                String accessToken = jwtProvider.generateTokenInfo(email).getAccessToken();
                cookieUtil.addCookie("Access-Token", accessToken,
                                     jwtProvider.getACCESS_TOKEN_TIME() + 60,
                                     response);

                log.info("Access token reissued for user: {}", email);
                refreshToken.reIssueAccessToken(accessToken);
                refreshTokenRepository.save(refreshToken);
                log.info("Refresh token updated in Redis for user: {}", email);
            },
            () -> {
                log.warn("Refresh token not found for email: {}", email);
                cookieUtil.removeCookie("Access-Token", response);
                throw new RefreshTokenNotFoundException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
            }
        );
    }

    public void setAuthentication(String email, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                email,
                "",
                Collections.singleton(new SimpleGrantedAuthority("USER"))
            );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    private String extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Access-Token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void respondWithInvalidTokenError(HttpServletResponse response)
        throws IOException {
        response.setStatus(404);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String body = "{\n"
            + "              \"status\" : \"FAILED\",\n"
            + "              \"message\" : \"토큰 형식이 유효하지 않습니다.\"\n"
            + "         }";
        response.getWriter().write(body);
    }

    private boolean shouldFilter(String path) {
        return filterPaths.keySet().stream()
                          .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    private Map initializeFilterPaths() {
        Map<String, Boolean> filterPaths = new HashMap<>();
        filterPaths.put("/api/v1/member", true);
        filterPaths.put("/api/v1/member/social", true);
        filterPaths.put("/api/v1/member/login/**", true);
        filterPaths.put("/api/v1/member/logout", true);
        filterPaths.put("/api/v1/member/nickname", true);
        filterPaths.put("/api/v1/member/email/**", true);
        filterPaths.put("/api/v1/member/password-reset", true);
        filterPaths.put("/api-docs/**", true);
        filterPaths.put("/v2/api-docs/**", true);
        filterPaths.put("/v3/api-docs/**", true);
        filterPaths.put("/webjars/**", true);
        filterPaths.put("/swagger/**", true);
        filterPaths.put("/swagger-ui/**", true);
        filterPaths.put("/swagger-config/**", true);
        filterPaths.put("/swagger-resources/**", true);
        return filterPaths;
    }
}
