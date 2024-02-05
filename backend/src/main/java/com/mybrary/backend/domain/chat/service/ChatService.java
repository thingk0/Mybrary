package com.mybrary.backend.domain.chat.service;

import com.mybrary.backend.domain.chat.dto.ChatMessageGetDto;
import com.mybrary.backend.domain.chat.dto.ChatMessagePostDto;
import com.mybrary.backend.domain.chat.dto.ChatRoomGetDto;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.security.core.Authentication;

public interface ChatService {

    List<ChatRoomGetDto> getAllChatRoom(Authentication authentication);

    void deleteChatRoom(Authentication authentication, Long chatRoomId);

    List<ChatMessageGetDto> getAllChatByChatRoomId(Authentication authentication, Long chatRoomId, Pageable page);

    List<ChatMessageGetDto> getAllChatByMemberId(Authentication authentication, Long memberId, Pageable page);

    void createChat(Authentication authentication, ChatMessagePostDto message);

}