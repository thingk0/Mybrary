package com.mybrary.backend.domain.chat.repository.custom;

import com.mybrary.backend.domain.chat.dto.responseDto.ChatMessageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatMessageRepositoryCustom {

    Page<ChatMessageResponseDto> getAllMessagesFromChatRoom(Long chatRoomId, Pageable pageable);
}
