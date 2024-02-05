package com.mybrary.backend.global.exception.member;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidNicknameException extends RuntimeException {

    private final ErrorCode errorCode;

    public InvalidNicknameException() {
        this.errorCode = ErrorCode.INVALID_NICKNAME;
    }
}
