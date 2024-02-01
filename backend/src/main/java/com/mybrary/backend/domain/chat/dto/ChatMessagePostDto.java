package com.mybrary.backend.domain.chat.dto;

import com.mybrary.backend.domain.contents.thread.dto.ThreadSimpleGetDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessagePostDto {

    /**
     *  채팅 보낼 때 요청
     *  message or threadId(paperId) 둘 중 하나만 데이터 있고 나머지는 null
     */

    private Long chatRoomId;
    private Long receiverId;
    private String message;
    private Long threadId;

}
