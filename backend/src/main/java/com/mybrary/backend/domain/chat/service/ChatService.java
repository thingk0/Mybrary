package com.mybrary.backend.domain.chat.service;

import com.mybrary.backend.domain.chat.dto.ChatMessageGetDto;
import com.mybrary.backend.domain.chat.dto.ChatMessagePostDto;
import com.mybrary.backend.domain.chat.dto.ChatRoomGetDto;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.security.core.Authentication;

public interface ChatService {

    List<ChatRoomGetDto> getAllChatRoom(Long myId);

    void deleteChatRoom(Long myId, Long chatRoomId);

    List<ChatMessageGetDto> getAllChatByChatRoomId(Long myId, Long chatRoomId, Pageable page);

    List<ChatMessageGetDto> getAllChatByMemberId(Long myId, Long memberId, Pageable page);

    void createChat(Long myId, ChatMessagePostDto message);

    void threadShare(Long myId, ChatMessagePostDto message);

}