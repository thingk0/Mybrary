package com.mybrary.backend.global.exception.jwt;

import com.mybrary.backend.global.format.ErrorCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class AccessTokenNotFoundException extends AuthenticationException {

    private final ErrorCode errorCode;

    public AccessTokenNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
