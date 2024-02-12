package com.mybrary.backend.domain.chat.controller;

import com.mybrary.backend.domain.chat.dto.requestDto.MessageRequestDto;
import com.mybrary.backend.domain.chat.dto.responseDto.ChatMessageResponseDto;
import com.mybrary.backend.domain.chat.service.ChatService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final ChatService chatService;

    @MessageMapping("/chat/{chatRoomId}/send")
    @SendTo("/chatroom/{chatRoomId}")
    public ChatMessageResponseDto sendMessage(@DestinationVariable Long chatRoomId,
                                              Principal principal,
                                              MessageRequestDto message) {

        return chatService.save(principal.getName(), chatRoomId, message);
    }
}
