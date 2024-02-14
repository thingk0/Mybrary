package com.mybrary.backend.domain.chat.service;

import com.mybrary.backend.domain.chat.dto.requestDto.ChatMessagePostDto;
import com.mybrary.backend.domain.chat.dto.requestDto.MessageRequestDto;
import com.mybrary.backend.domain.chat.dto.responseDto.ChatMessageResponseDto;
import com.mybrary.backend.domain.chat.dto.responseDto.ChatRoomGetDto;
import com.mybrary.backend.domain.chat.dto.responseDto.ChatRoomResponseDto;
import com.mybrary.backend.domain.chat.dto.responseDto.TChatMessageGetDto;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatService {

    ChatMessageResponseDto save(String email, Long chatRoomId, MessageRequestDto messageRequestDto);

    Page<ChatRoomResponseDto> loadParticipatingChatRooms(String email, Pageable pageable);

    Page<ChatMessageResponseDto> loadMessagesByChatRoomId(String email, Long chatRoomId, Pageable pageable);

    List<ChatRoomGetDto> getAllChatRoom(String email, Pageable page);

    void deleteChatRoom(String email, Long chatRoomId);

    Map<String, Object> getAllChatByChatRoomId(String email, Long chatRoomId);

    Map<String, Object> getAllChatByMemberId(String email, Long memberId, Pageable page);

    Long createChat(String email, ChatMessagePostDto message);

    Long threadShare(String email, ChatMessagePostDto message);
}