package com.mybrary.backend.global.exception.member;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidPasswordException extends RuntimeException {

    private final ErrorCode errorCode;

    public InvalidPasswordException() {
        this.errorCode = ErrorCode.MEMBER_PASSWORD_MISMATCH;
    }
}
