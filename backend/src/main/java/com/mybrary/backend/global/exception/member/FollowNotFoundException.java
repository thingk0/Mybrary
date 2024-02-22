package com.mybrary.backend.global.exception.member;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class FollowNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public FollowNotFoundException() {
        this.errorCode = ErrorCode.FOLLOW_NOT_FOUND;
    }
}
