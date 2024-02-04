package com.mybrary.backend.global.exception.email;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class FailedMessageTransmissionException extends RuntimeException {

    private final ErrorCode errorCode;

    public FailedMessageTransmissionException() {
        this.errorCode = ErrorCode.EMAIL_SEND_FAILED;
    }
}
