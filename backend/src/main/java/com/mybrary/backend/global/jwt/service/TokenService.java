package com.mybrary.backend.global.jwt.service;

import com.mybrary.backend.global.exception.jwt.RefreshTokenNotFoundException;
import com.mybrary.backend.global.jwt.RefreshToken;
import com.mybrary.backend.global.jwt.TokenInfo;
import com.mybrary.backend.global.jwt.provider.TokenProvider;
import com.mybrary.backend.global.jwt.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public void saveToken(TokenInfo tokenInfo) {
        refreshTokenRepository.save(RefreshToken.builder()
                                                .email(tokenInfo.getEmail())
                                                .refreshToken(tokenInfo.getRefreshToken())
                                                .accessToken(tokenInfo.getAccessToken())
                                                .build());
    }

    public void removeToken(String refreshToken) {
        refreshTokenRepository.findByRefreshToken(refreshToken).ifPresent(refreshTokenRepository::delete);
    }


    @Transactional(readOnly = true)
    public RefreshToken getByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(RefreshTokenNotFoundException::new);
    }


    public String reIssueAccessToken(String accessToken) {
        String email = tokenProvider.extractEmail(accessToken);
        RefreshToken refreshToken = refreshTokenRepository.findById(email)
                                                          .orElseThrow(RefreshTokenNotFoundException::new);

        String updatedAccessToken = tokenProvider.generateTokenInfo(email).getAccessToken();
        refreshToken.updateAccessToken(updatedAccessToken);
        refreshTokenRepository.save(refreshToken);
        return updatedAccessToken;
    }

    public String extractAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }

    public String extractAccessToken(String authorization) {
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }

}
