package com.mybrary.backend.domain.chat.service;

import com.mybrary.backend.domain.chat.dto.ChatMessagePostDto;
import com.mybrary.backend.domain.chat.dto.ChatRoomGetDto;
import com.mybrary.backend.domain.chat.dto.TChatMessageGetDto;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.security.core.Authentication;

public interface ChatService {

    List<ChatRoomGetDto> getAllChatRoom(String email, Pageable page);

    void deleteChatRoom(String email, Long chatRoomId);

    List<TChatMessageGetDto> getAllChatByChatRoomId(String email, Long chatRoomId, Pageable page);

    List<TChatMessageGetDto> getAllChatByMemberId(String email, Long memberId, Pageable page);

    Long createChat(String email, ChatMessagePostDto message);

    Long threadShare(String email, ChatMessagePostDto message);

}