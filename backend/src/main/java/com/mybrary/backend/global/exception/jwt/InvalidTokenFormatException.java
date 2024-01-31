package com.mybrary.backend.global.exception.jwt;

import com.mybrary.backend.global.format.ErrorCode;
import io.jsonwebtoken.JwtException;
import lombok.Getter;

@Getter
public class InvalidTokenFormatException extends JwtException {

    private final ErrorCode errorCode;

    public InvalidTokenFormatException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
