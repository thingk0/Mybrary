package com.mybrary.backend.domain.chat.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessagePostDto {

    /**
     * 채팅 보낼 때 요청 message
     */

    private Long chatRoomId;
    private Long receiverId;
    private String message;
    private Long threadId;

}
