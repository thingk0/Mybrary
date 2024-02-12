package com.mybrary.backend.global.exception.chat;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidChatRoomAccessException extends RuntimeException {

    private final ErrorCode errorCode;

    public InvalidChatRoomAccessException() {
        this.errorCode = ErrorCode.INVALID_CHATROOM_ACCESS;
    }
}

