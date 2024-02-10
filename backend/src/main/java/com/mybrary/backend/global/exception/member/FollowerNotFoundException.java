package com.mybrary.backend.global.exception.member;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class FollowerNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public FollowerNotFoundException() {
        this.errorCode = ErrorCode.FOLLOWER_NOT_FOUND;
    }
}
