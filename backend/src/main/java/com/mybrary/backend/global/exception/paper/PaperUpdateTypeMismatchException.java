package com.mybrary.backend.global.exception.paper;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class PaperUpdateTypeMismatchException extends IllegalArgumentException {

    private final ErrorCode errorCode;

    public PaperUpdateTypeMismatchException() {
        this.errorCode = ErrorCode.PAPER_UPDATE_TYPE_MISMATCH;
    }
}
