package com.mybrary.backend.global.config;

import com.mybrary.backend.global.filter.EmailVerificationFilter;
import com.mybrary.backend.global.handler.TokenExceptionFilterHandler;
import com.mybrary.backend.global.jwt.JwtFilter;
import com.mybrary.backend.global.jwt.JwtProvider;
import com.mybrary.backend.global.jwt.repository.RefreshTokenRepository;
import com.mybrary.backend.global.util.CookieUtil;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final CookieUtil cookieUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenExceptionFilterHandler tokenExceptionFilterHandler;
    private final EmailVerificationFilter emailVerificationFilter;

    @Value("${cors.allowed.origin}")
    private String CORS_ALLOWED_URL;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {

        security
            .httpBasic(basic -> basic.disable())
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Collections.singletonList(CORS_ALLOWED_URL));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setMaxAge(3600L);
                return config;
            }));

        security
            .authorizeHttpRequests((authorize ->

            {
                authorize.requestMatchers(
                    "/api-docs/**",
                    "/v2/api-docs/**",
                    "/v3/api-docs/**",
                    "/webjars/**",
                    "/swagger/**",
                    "/swagger-ui/**",
                    "/swagger-config/**",
                    "/swagger-resources/**",
                    "/api/v1/member",
                    "/api/v1/member/social",
                    "/api/v1/member/login/**",
                    "/api/v1/member/logout",
                    "/api/v1/member/nickname",
                    "/api/v1/member/email/**",
                    "/api/v1/member/password-reset"
                ).permitAll();
                authorize.anyRequest().authenticated();
            }));

        security
            .sessionManagement(sessionManager ->

                               {
                                   sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                               });

        security
            .addFilterBefore(emailVerificationFilter,
                             UsernamePasswordAuthenticationFilter.class)
                .

            addFilterBefore(tokenExceptionFilterHandler,
                            UsernamePasswordAuthenticationFilter.class)
                .

            addFilterBefore(new JwtFilter(jwtProvider, cookieUtil, refreshTokenRepository),

                            UsernamePasswordAuthenticationFilter.class);

        return security.build();
    }

}
