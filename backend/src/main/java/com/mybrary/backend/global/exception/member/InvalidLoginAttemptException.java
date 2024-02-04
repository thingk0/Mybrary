package com.mybrary.backend.global.exception.member;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidLoginAttemptException extends RuntimeException {

    private final ErrorCode errorCode;

    public InvalidLoginAttemptException() {
        this.errorCode = ErrorCode.MEMBER_LOGIN_FAILED;
    }
}
