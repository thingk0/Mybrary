package com.mybrary.backend.domain.chat.controller;

import com.mybrary.backend.domain.chat.dto.requestDto.MessageRequestDto;
import com.mybrary.backend.domain.chat.dto.responseDto.ChatMessageResponseDto;
import com.mybrary.backend.domain.chat.service.ChatService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Log4j2
@Controller
@RequiredArgsConstructor
public class MessageController {

    private final ChatService chatService;
    private final SimpMessagingTemplate template;

    @MessageMapping("/chat/{chatRoomId}/send")
    public void sendMessage(@DestinationVariable Long chatRoomId,
                            Principal principal,
                            MessageRequestDto message) {

        log.info("method=sendMessage chatRoomId={}, email={}, message={}",
                 chatRoomId, principal.getName(), message.getMessage());

        ChatMessageResponseDto content = chatService.save(principal.getName(), chatRoomId, message);
        template.convertAndSend("/sub/chatMemberId/" + content.getReceiverId(), content);
    }
}
