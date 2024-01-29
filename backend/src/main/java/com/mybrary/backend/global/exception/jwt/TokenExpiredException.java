package com.mybrary.backend.global.exception.jwt;

import com.mybrary.backend.global.format.ErrorCode;
import io.jsonwebtoken.JwtException;
import lombok.Getter;

@Getter
public class TokenExpiredException extends JwtException {

    private final ErrorCode errorCode;

    public TokenExpiredException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
