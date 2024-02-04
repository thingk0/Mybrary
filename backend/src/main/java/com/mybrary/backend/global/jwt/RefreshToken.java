package com.mybrary.backend.global.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@AllArgsConstructor
@RedisHash(value = "RefreshToken", timeToLive = 60 * 60 * 24 * 7)
public class RefreshToken {

    @Id
    private String email;

    @Indexed
    private String refreshToken;

    private String accessToken;

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
