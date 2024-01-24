package com.mybrary.backend.domain.chat.controller;

import com.mybrary.backend.domain.chat.chat_message.entity.ChatMessage;
import com.mybrary.backend.domain.chat.dto.ChatMessageGetDto;
import com.mybrary.backend.domain.chat.dto.ChatRoomGetDto;
import com.mybrary.backend.domain.chat.service.ChatServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Chat 컨트롤러", description = "Chat Controller API")
@RestController
@RequestMapping("/api/v1/chat")
public class ChatControllerV1 {

    @Autowired
    private ChatServiceImpl chatService;

    @Operation(summary = "채팅방 리스트 조회", description = "자신의 채팅방 리스트 조회")
    @GetMapping
    public ResponseEntity<List<ChatRoomGetDto>> getAllChatRoomList() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "채팅방 나가기", description = "채팅방 나가기 (채팅참여 삭제처리)")
    @DeleteMapping
    public ResponseEntity<?> deleteChatRoom() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "채팅방의 메세지 리스트 조회", description = "채팅방의 메세지 리스트 조회")
    @GetMapping("/{chatRoomId}/message")
    public ResponseEntity<?> getAllChatList(@PathVariable Long chatRoomId) {
        return new ResponseEntity<List<ChatMessageGetDto>>(HttpStatus.OK);
    }

    @Operation(summary = "채팅방이 없는 사람과의 메세지 리스트 조회", description = "한번도 안했으면 리스트가 비어있고, 채팅방을 나간거면 리스트가 있음")
    @GetMapping("/message")
    public ResponseEntity<?> enterChatRoom(@RequestParam Long memberId) {
        return new ResponseEntity<List<ChatMessageGetDto>>(HttpStatus.OK);
    }

    @Operation(summary = "채팅 메세지 보내기", description = "채팅 메세지 보내기")
    @PostMapping("/{chatRoomId}/message")
    public ResponseEntity<?> createChat(@PathVariable Long chatRoomId, @RequestBody ChatMessage message) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
