package com.mybrary.backend.global.jwt.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@AllArgsConstructor
@RedisHash(value = "refresh_token", timeToLive = 60 * 60 * 24)
public class RefreshToken {

    @Id
    private String key;

    @Indexed
    private String value;

    private String accessToken;

    public void reIssueAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
