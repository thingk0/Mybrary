package com.mybrary.backend.global.exception.scrap;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class ScrapNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public ScrapNotFoundException() {this.errorCode = ErrorCode.SCRAP_NOT_FOUND; }
}
