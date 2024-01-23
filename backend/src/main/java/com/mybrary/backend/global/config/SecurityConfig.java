package com.mybrary.backend.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {

        security
            .httpBasic(basic -> basic.disable())
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable());

        security
            .sessionManagement(sessionManager -> {
                sessionManager.disable();
                sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            });

        security
            .authorizeHttpRequests((authorize -> {
//                authorize.requestMatchers("/**");
                authorize.anyRequest().permitAll();
            }));

        return security.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                           .requestMatchers(
                               "/v2/api-docs",
                               "/v3/api-docs",
                               "/swagger-ui/**",
                               "/webjars/**",
                               "/swagger/**",
                               "/api-docs/**",
                               "/swagger-ui/**",
                               "/swagger-resources/**"
                           );
    }


}
