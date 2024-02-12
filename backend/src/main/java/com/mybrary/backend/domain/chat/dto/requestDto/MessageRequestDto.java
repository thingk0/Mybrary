package com.mybrary.backend.domain.chat.dto.requestDto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageRequestDto {

    private Long chatRoomId;
    private Long senderId;
    private String message;
    private Long threadId;

}
