package com.mybrary.backend.global.exception.thread;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class MainThreadListNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public MainThreadListNotFoundException() {
        this.errorCode = ErrorCode.THREADID_NOT_FOUND;
    }
}
