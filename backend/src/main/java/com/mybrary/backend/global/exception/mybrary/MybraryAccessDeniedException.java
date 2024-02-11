package com.mybrary.backend.global.exception.mybrary;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class MybraryAccessDeniedException extends RuntimeException {

    private final ErrorCode errorCode;

    public MybraryAccessDeniedException() {
        this.errorCode = ErrorCode.MYBRARY_ACCESS_DENIED;
    }
}
