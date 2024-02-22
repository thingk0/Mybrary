package com.mybrary.backend.global.exception.member;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class FollowingNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public FollowingNotFoundException() {
        this.errorCode = ErrorCode.FOLLOWING_NOT_FOUND;
    }
}
