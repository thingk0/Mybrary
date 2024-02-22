package com.mybrary.backend.global.exception.thread;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class ThreadIdNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public ThreadIdNotFoundException() {
        this.errorCode = ErrorCode.THREADID_NOT_FOUND;
    }
}
