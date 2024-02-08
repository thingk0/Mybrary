package com.mybrary.backend.global.exception.paper;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class PaperListNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public PaperListNotFoundException() {this.errorCode = ErrorCode.PAPERLIST_NOT_FOUND; }
}
