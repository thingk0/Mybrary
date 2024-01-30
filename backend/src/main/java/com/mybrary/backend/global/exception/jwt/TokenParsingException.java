package com.mybrary.backend.global.exception.jwt;

import com.mybrary.backend.global.format.ErrorCode;
import io.jsonwebtoken.JwtException;
import lombok.Getter;

@Getter
public class TokenParsingException extends JwtException {

    private final ErrorCode errorCode;

    public TokenParsingException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
