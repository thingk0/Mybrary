package com.mybrary.backend.global.exception.paper;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class PaperDeleteException extends RuntimeException {

    private final ErrorCode errorCode;

    public PaperDeleteException() {this.errorCode = ErrorCode.PAPER_DELETE_FAILED; }
}
