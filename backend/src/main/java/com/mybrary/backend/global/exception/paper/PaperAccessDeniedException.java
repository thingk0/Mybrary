package com.mybrary.backend.global.exception.paper;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class PaperAccessDeniedException extends RuntimeException {

    private final ErrorCode errorCode;

    public PaperAccessDeniedException() {
        this.errorCode = ErrorCode.PAPER_ACCESS_DENIED;
    }
}
