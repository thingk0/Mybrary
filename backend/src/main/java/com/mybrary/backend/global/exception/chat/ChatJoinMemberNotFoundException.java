package com.mybrary.backend.global.exception.chat;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class ChatJoinMemberNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public ChatJoinMemberNotFoundException() {
        this.errorCode = ErrorCode.CHAT_JOIN_MEMBER_NOT_FOUND;
    }
}

