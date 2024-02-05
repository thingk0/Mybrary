package com.mybrary.backend.global.jwt;

import com.mybrary.backend.global.exception.jwt.InvalidTokenFormatException;
import com.mybrary.backend.global.exception.jwt.InvalidTokenSignatureException;
import com.mybrary.backend.global.format.ErrorCode;
import com.mybrary.backend.global.jwt.dto.TokenInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Log4j2
@Component
public class JwtProvider {

    @Getter
    @Value("${jwt.ACCESS_TIME}")
    private int ACCESS_TOKEN_TIME;

    @Getter
    @Value("${jwt.REFRESH_TIME}")
    private int REFRESH_TOKEN_TIME;

    @Value("${jwt.SECRET_KEY}")
    private String SECRET_KEY;

    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));
    }

    public TokenInfo generateTokenInfo(String email) {
        return TokenInfo.builder()
                        .email(email)
                        .accessToken(createAccessToken(email))
                        .refreshToken(createRefreshToken())
                        .build();
    }

    private String createAccessToken(String email) {
        return Jwts.builder()
                   .setSubject(email)
                   .setExpiration(new Date(
                       System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(ACCESS_TOKEN_TIME)))
                   .signWith(key)
                   .compact();
    }

    private String createRefreshToken() {
        return Jwts.builder()
                   .setExpiration(new Date(
                       System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(REFRESH_TOKEN_TIME)))
                   .signWith(key)
                   .compact();
    }

    private Claims parseClaims(String token) {
        try {
            Claims body = Jwts.parser()
                              .setSigningKey(key)
                              .parseClaimsJws(token)
                              .getBody();
            return body;
        } catch (ExpiredJwtException e) {
            log.info("만료된 토큰, 사용자 이메일: {}", e.getClaims().getSubject());
            return e.getClaims();
        }
    }

    protected boolean verifyToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException e) {
            return false;
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            throw new InvalidTokenFormatException(ErrorCode.INVALID_TOKEN_TYPE);
        } catch (SignatureException e) {
            throw new InvalidTokenSignatureException(ErrorCode.MODIFIED_TOKEN_DETECTED);
        }
        return true;
    }

    public String extractEmail(String token) {
        return parseClaims(token).getSubject();
    }

}
