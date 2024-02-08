package com.mybrary.backend.global.exception.tag;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class TagNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public TagNotFoundException() {
        this.errorCode = ErrorCode.TAG_NOT_FOUND;
    }
}
