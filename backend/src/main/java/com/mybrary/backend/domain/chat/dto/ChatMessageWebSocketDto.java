package com.mybrary.backend.domain.chat.dto;

import com.mybrary.backend.domain.member.dto.MemberInfoDto;
import java.time.LocalDateTime;
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
public class ChatMessageWebSocketDto {

    /**
     *  채팅메세지 조회
     *  채팅메시지 목록 조회에서 List 안에 넣어서 사용
     */

    private Long chatId;
    private MemberInfoDto sender;
    private String message;
    private Long threadId;
    private boolean isRead;
    private LocalDateTime createdAt;

}
