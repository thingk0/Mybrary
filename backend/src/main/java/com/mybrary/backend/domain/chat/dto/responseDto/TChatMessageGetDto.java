package com.mybrary.backend.domain.chat.dto.responseDto;

import com.mybrary.backend.domain.contents.thread.dto.responseDto.ThreadShareGetDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TChatMessageGetDto {

    /**
     *  채팅메세지 조회
     *  채팅메시지 목록 조회에서 List 안에 넣어서 사용
     */

    private Long chatId;

    private Long senderId;

    private String message;

    private ThreadShareGetDto thread;

    private boolean isRead;
    private LocalDateTime createdAt;

}
