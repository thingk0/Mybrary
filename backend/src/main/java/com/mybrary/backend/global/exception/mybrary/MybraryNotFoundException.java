package com.mybrary.backend.global.exception.mybrary;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class MybraryNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public MybraryNotFoundException() {
        this.errorCode = ErrorCode.MYBRARY_NOT_FOUND;
    }
}
