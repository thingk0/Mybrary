package com.mybrary.backend.domain.chat.dto;

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

    private Long chatRoomId;
    private String message;
//    private Thread thread; // Thread의 Dto로 바꿔야함

}
