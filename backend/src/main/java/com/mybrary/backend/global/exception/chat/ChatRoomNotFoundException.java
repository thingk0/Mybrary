package com.mybrary.backend.global.exception.chat;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class ChatRoomNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public ChatRoomNotFoundException() {
        this.errorCode = ErrorCode.CHAT_ROOM_NOT_FOUND;
    }
}

