package com.mybrary.backend.global.jwt;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenInfo {

    private String email;
    private String accessToken;
    private String refreshToken;

}
