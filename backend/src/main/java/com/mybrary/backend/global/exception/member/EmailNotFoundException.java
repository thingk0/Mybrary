package com.mybrary.backend.global.exception.member;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class EmailNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public EmailNotFoundException() {
        this.errorCode = ErrorCode.MEMBER_EMAIL_NOT_FOUND;
    }
}
