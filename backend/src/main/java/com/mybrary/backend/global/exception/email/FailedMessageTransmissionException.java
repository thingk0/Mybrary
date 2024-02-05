package com.mybrary.backend.global.exception.email;

import com.mybrary.backend.global.format.ErrorCode;
import lombok.Getter;

@Getter
public class FailedMessageTransmissionException extends RuntimeException {

    private final ErrorCode errorCode;

    public FailedMessageTransmissionException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
