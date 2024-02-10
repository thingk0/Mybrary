package com.mybrary.backend.global.exception.pickbook;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class PickBookNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public PickBookNotFoundException() {
        this.errorCode = ErrorCode.PICKBOOK_NOT_FOUND;
    }
}
