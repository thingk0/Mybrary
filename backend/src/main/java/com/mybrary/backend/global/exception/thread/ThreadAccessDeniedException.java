package com.mybrary.backend.global.exception.thread;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class ThreadAccessDeniedException extends RuntimeException {

    private final ErrorCode errorCode;

    public ThreadAccessDeniedException() {
        this.errorCode = ErrorCode.THREAD_ACCESS_DENIED;
    }
}
