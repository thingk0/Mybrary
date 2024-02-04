package com.mybrary.backend.domain.chat.controller;

import com.mybrary.backend.domain.chat.dto.ChatMessageGetDto;
import com.mybrary.backend.domain.chat.dto.ChatMessagePostDto;
import com.mybrary.backend.domain.chat.dto.ChatRoomGetDto;
import com.mybrary.backend.domain.chat.service.ChatService;
import com.mybrary.backend.domain.member.dto.MemberInfoDto;
import com.mybrary.backend.global.format.code.ApiResponse;
import com.mybrary.backend.global.format.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

    @Operation(summary = "채팅방 리스트 조회", description = "자신의 채팅방 리스트 조회")
    @GetMapping
    public ResponseEntity<?> getAllChatRoom(
        @Parameter(hidden = true) Authentication authentication) {

        MemberInfoDto joinMember1 = new MemberInfoDto(1L, "wnsgh", "안녕하세요 최준호입니다", "123123");
        MemberInfoDto joinMember2 = new MemberInfoDto(2L, "aksrl", "안녕하세요 서만기입니다", "666666");
        MemberInfoDto joinMember3 = new MemberInfoDto(3L, "gPtjs", "안녕하세요 박혜선입니다", "145643");
        MemberInfoDto joinMember4 = new MemberInfoDto(4L, "thdud", "안녕하세요 최소영입니다", "000000");

        ChatRoomGetDto chatRoom1 = new ChatRoomGetDto(1L, joinMember1, "명성아 넌 천재야", null, 2);
        ChatRoomGetDto chatRoom2 = new ChatRoomGetDto(2L, joinMember2, "내가 한 거 볼래?", null, 0);
        ChatRoomGetDto chatRoom3 = new ChatRoomGetDto(3L, joinMember3, "명성아 나 이것 좀 알려줘", null, 6);
        ChatRoomGetDto chatRoom4 = new ChatRoomGetDto(4L, joinMember4, "게릿힘들어...", null, 3);

        List<ChatRoomGetDto> list = new ArrayList<>();
        list.add(chatRoom1);
        list.add(chatRoom2);
        list.add(chatRoom3);
        list.add(chatRoom4);

        List<ChatRoomGetDto> result = chatService.getAllChatRoom(authentication);

        return response.success(ResponseCode.CHATROOM_LIST_FETCHED.getMessage(), result);
    }

    @Operation(summary = "채팅방 나가기", description = "채팅방 나가기 (채팅참여 삭제처리)")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChatRoom(@Parameter(hidden = true) Authentication authentication,
                                            @PathVariable(name = "id") Long chatRoomId) {

        return response.success(ResponseCode.CHATROOM_EXITED.getMessage(), chatRoomId);
    }

    @Operation(summary = "채팅방의 메세지 리스트 조회", description = "채팅방의 메세지 리스트 조회")
    @GetMapping("/{id}/message")
    public ResponseEntity<?> getAllChatByChatRoomId(
        @Parameter(hidden = true) Authentication authentication,
        @PathVariable(name = "id") Long chatRoomId,
        @PageableDefault(page = 0, size = 10) Pageable page) {

        MemberInfoDto sender1 = new MemberInfoDto(1L, "wnsgh", "안녕하세요 최준호입니다", "123123");
        MemberInfoDto sender2 = new MemberInfoDto(2L, "audtjd", "안녕하세요 고명성입니다", "123123");

        ChatMessageGetDto message1 = new ChatMessageGetDto(1L, sender1, "명성아 넌 천재야", null, false,
                                                           null);
        ChatMessageGetDto message2 = new ChatMessageGetDto(2L, sender1, "인프라 대장 고명성", null, false,
                                                           null);
        ChatMessageGetDto message3 = new ChatMessageGetDto(3L, sender2, "나 인프라 끝냈어", null, true,
                                                           null);
        ChatMessageGetDto message4 = new ChatMessageGetDto(4L, sender2, "아직 인프라중", null, true,
                                                           null);
        ChatMessageGetDto message5 = new ChatMessageGetDto(5L, sender2, "젠킨스 해야해..", null, true,
                                                           null);

        List<ChatMessageGetDto> list = new ArrayList<>();
        list.add(message1);
        list.add(message2);
        list.add(message3);
        list.add(message4);
        list.add(message5);

        List<ChatMessageGetDto> result = chatService.getAllChatByChatRoomId(authentication,
                                                                            chatRoomId, page);

        HashMap<String, Object> map = new HashMap<>();
        map.put("chatMessageList", result);
        map.put("page", page);

        return response.success(ResponseCode.CHAT_MESSAGES_FETCHED.getMessage(), map);
    }

    @Operation(summary = "회원정보에서 채팅 시작", description = "한번도 안했으면 리스트가 비어있고, 아니면 리스트가 있음")
    @GetMapping("/message")
    public ResponseEntity<?> getAllChatByMemberId(
        @Parameter(hidden = true) Authentication authentication, @RequestParam Long memberId,
        @PageableDefault(page = 0, size = 10) Pageable page) {
        MemberInfoDto sender1 = new MemberInfoDto(1L, "wnsgh", "안녕하세요 최준호입니다", "123123");
        MemberInfoDto sender2 = new MemberInfoDto(2L, "audtjd", "안녕하세요 고명성입니다", "123123");

        ChatMessageGetDto message1 = new ChatMessageGetDto(1L, sender1, "명성아 넌 천재야", null, false,
                                                           null);
        ChatMessageGetDto message2 = new ChatMessageGetDto(2L, sender1, "인프라 대장 고명성", null, false,
                                                           null);
        ChatMessageGetDto message3 = new ChatMessageGetDto(3L, sender2, "나 인프라 끝냈어", null, true,
                                                           null);
        ChatMessageGetDto message4 = new ChatMessageGetDto(4L, sender2, "아직 인프라중", null, true,
                                                           null);
        ChatMessageGetDto message5 = new ChatMessageGetDto(5L, sender2, "젠킨스 해야해..", null, true,
                                                           null);

        List<ChatMessageGetDto> list = new ArrayList<>();
        list.add(message1);
        list.add(message2);
        list.add(message3);
        list.add(message4);
        list.add(message5);

        List<ChatMessageGetDto> emptyList = new ArrayList<>();

        List<ChatMessageGetDto> result = chatService.getAllChatByMemberId(authentication, memberId, page);

        HashMap<String, Object> map = new HashMap<>();
        map.put("chatMessageList", result);
        map.put("page", page);

        return response.success(ResponseCode.CHATROOM_ENTERED.getMessage(), map);
    }

    @Operation(summary = "채팅 메세지 보내기", description = "채팅 메세지 보내기")
    @PostMapping("/{id}/message")
    public ResponseEntity<?> createChat(@Parameter(hidden = true) Authentication authentication,
                                        @PathVariable("id") Long chatRoomId,
                                        @RequestBody ChatMessagePostDto message) {

        // TODO: 기존 코드에 @PathVariable("id") Long chatRoomId 이 빠져있었음. 해당 파라미터도 포함해서 로직 완성 !
        chatService.createChat(authentication, message);
        return response.success(ResponseCode.CHAT_MESSAGE_SENT.getMessage());
    }

}
