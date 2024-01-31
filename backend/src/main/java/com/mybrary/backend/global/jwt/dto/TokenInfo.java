package com.mybrary.backend.global.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo {

    private String email;
    private String accessToken;
    private String refreshToken;

}
