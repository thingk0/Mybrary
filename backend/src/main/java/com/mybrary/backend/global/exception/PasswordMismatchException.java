package com.mybrary.backend.global.exception;

import com.mybrary.backend.global.format.ErrorCode;
import lombok.Getter;

@Getter
public class PasswordMismatchException extends RuntimeException {

    private final ErrorCode errorCode;

    public PasswordMismatchException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
