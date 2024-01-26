package com.mybrary.backend.domain.chat.controller;

import com.mybrary.backend.domain.chat.entity.ChatMessage;
import com.mybrary.backend.domain.chat.dto.ChatMessageGetDto;
import com.mybrary.backend.domain.chat.dto.ChatRoomGetDto;
import com.mybrary.backend.domain.chat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatControllerV1 {

    private final ChatService chatService;

    @Operation(summary = "채팅방 리스트 조회", description = "자신의 채팅방 리스트 조회")
    @GetMapping
    public ResponseEntity<List<ChatRoomGetDto>> getAllChatRoom() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "채팅방 나가기", description = "채팅방 나가기 (채팅참여 삭제처리)")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChatRoom(@PathVariable(name = "id") Long chatRoomId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "채팅방의 메세지 리스트 조회", description = "채팅방의 메세지 리스트 조회")
    @GetMapping("/{id}/message")
    public ResponseEntity<?> getAllChatByChatRoomId(@PathVariable(name = "id") Long chatRoomId,
                                            @PageableDefault(page = 0, size = 10) Pageable page) {
        return new ResponseEntity<List<ChatMessageGetDto>>(HttpStatus.OK);
    }

    @Operation(summary = "회원정보에서 채팅 시작", description = "한번도 안했으면 리스트가 비어있고, 아니면 리스트가 있음")
    @GetMapping("/message")
    public ResponseEntity<?> getAllChatByMemberId(@RequestParam Long memberId,
                                           @PageableDefault(page = 0, size = 10) Pageable page) {
        return new ResponseEntity<List<ChatMessageGetDto>>(HttpStatus.OK);
    }

    @Operation(summary = "채팅 메세지 보내기", description = "채팅 메세지 보내기")
    @PostMapping("/{id}/message")
    public ResponseEntity<?> createChat(@PathVariable(name = "id") Long chatRoomId,
                                        @RequestBody ChatMessage message) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
