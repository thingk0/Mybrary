package com.mybrary.backend.domain.chat.dto;

import com.mybrary.backend.domain.member.dto.MemberInfoDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageGetDto {

    /**
     *  채팅메세지 조회
     *  채팅메시지 목록 조회에서 List 안에 넣어서 사용
     */

    private Long chatId;
    private MemberInfoDto sender;
    private String message;
    private Long threadId;
    private String threadImageUrl;
    private Long writerId;
    private String writerNickname;
    private String writerImageUrl;
    private boolean isRead;
    private LocalDateTime createdAt;

}
