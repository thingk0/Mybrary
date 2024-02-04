package com.mybrary.backend.global.exception.jwt;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class RefreshTokenNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public RefreshTokenNotFoundException() {
        this.errorCode = ErrorCode.REFRESH_TOKEN_NOT_FOUND;
    }
}
