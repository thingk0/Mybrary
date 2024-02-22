package com.mybrary.backend.global.exception;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class RollingPaperNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public RollingPaperNotFoundException() {
        this.errorCode = ErrorCode.ROLLING_PAPER_NOT_FOUND;
    }
}
