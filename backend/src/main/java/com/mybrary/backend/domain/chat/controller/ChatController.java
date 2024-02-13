package com.mybrary.backend.domain.chat.controller;

import com.mybrary.backend.domain.chat.dto.requestDto.ChatMessagePostDto;
import com.mybrary.backend.domain.chat.dto.responseDto.ChatRoomGetDto;
import com.mybrary.backend.domain.chat.dto.responseDto.TChatMessageGetDto;
import com.mybrary.backend.domain.chat.service.ChatService;
import com.mybrary.backend.domain.member.dto.responseDto.MemberInfoDto;
import com.mybrary.backend.domain.member.service.MemberService;
import com.mybrary.backend.global.format.code.ApiResponse;
import com.mybrary.backend.global.format.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
public class ChatController {

    private final ApiResponse response;
    private final ChatService chatService;
    private final MemberService memberService;

    @Operation(summary = "채팅방 리스트 조회", description = "자신의 채팅방 리스트 조회")
    @GetMapping
    public ResponseEntity<?> getAllChatRoom(@Parameter(hidden = true) Authentication authentication,
                                            @PageableDefault(page = 0, size = 20) Pageable page) {

        return response.success(ResponseCode.CHATROOM_LIST_FETCHED,
                                chatService.loadParticipatingChatRooms(authentication.getName(), page));
    }

    @Operation(summary = "채팅방 나가기", description = "채팅방 나가기 (채팅참여 삭제처리)")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChatRoom(@Parameter(hidden = true) Authentication authentication,
                                            @PathVariable(name = "id") Long chatRoomId) {

        chatService.deleteChatRoom(authentication.getName(), chatRoomId);
        return response.success(ResponseCode.CHATROOM_EXITED, chatRoomId);
    }

    @Operation(summary = "채팅방의 메세지 리스트 조회", description = "채팅방의 메세지 리스트 조회")
    @GetMapping("/{id}/message")
    public ResponseEntity<?> getAllChatByChatRoomId(@Parameter(hidden = true) Authentication authentication,
                                                    @PathVariable(name = "id") Long chatRoomId,
                                                    @PageableDefault(page = 0, size = 20) Pageable page) {
        return response.success(ResponseCode.CHAT_MESSAGES_FETCHED,
                                chatService.loadMessagesByChatRoomId(authentication.getName(), chatRoomId, page));
    }

    @Operation(summary = "회원정보에서 채팅 시 작", description = "한번도 안했으면 리스트가 비어있고, 아니면 리스트가 있음")
    @GetMapping("/message")
    public ResponseEntity<?> getAllChatByMemberId(@Parameter(hidden = true) Authentication authentication,
                                                  @RequestParam Long memberId,
                                                  @PageableDefault(page = 0, size = 20) Pageable page) {

        Map<String, Object> result = chatService.getAllChatByMemberId(authentication.getName(), memberId, page);
        result.put("page", page);

        return response.success(ResponseCode.CHATROOM_ENTERED, result);
    }

    @Operation(summary = "채팅 메세지 보내기", description = "채팅 메세지 보내기")
    @PostMapping("/{id}/message")
    public ResponseEntity<?> createChat(@Parameter(hidden = true) Authentication authentication,
                                        @PathVariable("id") Long chatRoomId,
                                        @RequestBody ChatMessagePostDto message) {
        // TODO: 기존 코드에 @PathVariable("id") Long chatRoomId 이 빠져있었음. 해당 파라미터도 포함해서 로직 완성 !
        Long chatId = chatService.createChat(authentication.getName(), message);
        return response.success(ResponseCode.CHAT_MESSAGE_SENT, chatId);
    }

}
