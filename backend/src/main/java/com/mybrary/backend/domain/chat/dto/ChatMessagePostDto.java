package com.mybrary.backend.domain.chat.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessagePostDto {

    /**
     * 채팅 보낼 때 요청 message
     */

    private Long chatRoomId;
    private Long receiverId;
    private String message;
    private Long threadId;

}
