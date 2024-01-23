package com.mybrary.backend.domain.chat.dto;

import com.mybrary.backend.domain.member.dto.MemberGetDto;
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
public class ChatMessageGetDto {

    private Long chatId;
    private MemberGetDto sender;
    private String message;
//    private Threads thread; // Thread의 Dto로 바꿔야함
    private boolean isRead;
    private String time;

}
