package com.mybrary.backend.global.exception.mybrary;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class NotMybraryException extends RuntimeException {

    private final ErrorCode errorCode;

    public NotMybraryException() {
        this.errorCode = ErrorCode.MYBRARY_MISMATCH;
    }
}
