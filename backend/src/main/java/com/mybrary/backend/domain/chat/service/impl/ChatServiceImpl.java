package com.mybrary.backend.domain.chat.service.impl;

import com.mybrary.backend.domain.chat.dto.requestDto.ChatMessagePostDto;
import com.mybrary.backend.domain.chat.dto.requestDto.MessageRequestDto;
import com.mybrary.backend.domain.chat.dto.responseDto.ChatMessageResponseDto;
import com.mybrary.backend.domain.chat.dto.responseDto.ChatRoomGetDto;
import com.mybrary.backend.domain.chat.dto.responseDto.ChatRoomResponseDto;
import com.mybrary.backend.domain.chat.dto.responseDto.TChatMessageGetDto;
import com.mybrary.backend.domain.chat.dto.responseDto.TChatMessageWebSocketGetDto;
import com.mybrary.backend.domain.chat.entity.ChatJoin;
import com.mybrary.backend.domain.chat.entity.ChatMessage;
import com.mybrary.backend.domain.chat.entity.ChatRoom;
import com.mybrary.backend.domain.chat.repository.ChatJoinRepository;
import com.mybrary.backend.domain.chat.repository.ChatMessageRepository;
import com.mybrary.backend.domain.chat.repository.ChatRoomRepository;
import com.mybrary.backend.domain.chat.service.ChatService;
import com.mybrary.backend.domain.contents.thread.dto.responseDto.ThreadShareGetDto;
import com.mybrary.backend.domain.contents.thread.repository.ThreadRepository;
import com.mybrary.backend.domain.member.dto.responseDto.MemberInfoDto;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import com.mybrary.backend.domain.member.service.MemberService;
import com.mybrary.backend.global.exception.chat.ChatJoinMemberNotFoundException;
import com.mybrary.backend.global.exception.chat.ChatRoomNotFoundException;
import com.mybrary.backend.global.exception.chat.InvalidChatRoomAccessException;
import com.mybrary.backend.global.exception.member.EmailNotFoundException;
import com.mybrary.backend.global.exception.member.MemberNotFoundException;
import com.mybrary.backend.global.jwt.service.TokenService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatJoinRepository chatJoinRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final ThreadRepository threadRepository;
    private final TokenService tokenService;
    private final SimpMessagingTemplate messagingTemplate;

    @Async
    public CompletableFuture<Member> findSenderAsync(String email) {
        return CompletableFuture.completedFuture(
            memberRepository.searchByEmail(email).orElseThrow(EmailNotFoundException::new)
        );
    }

    @Async
    public CompletableFuture<ChatRoom> findChatRoomAsync(Long chatRoomId) {
        return CompletableFuture.completedFuture(
            chatRoomRepository.findById(chatRoomId).orElseThrow(ChatRoomNotFoundException::new)
        );
    }

    @Transactional
    @Override
    public ChatMessageResponseDto save(String email, Long chatRoomId, MessageRequestDto requestDto) {

        CompletableFuture<Member> senderFuture = findSenderAsync(email);
        CompletableFuture<ChatRoom> chatRoomFuture = findChatRoomAsync(chatRoomId);

        Member sender = senderFuture.join();
        ChatRoom chatRoom = chatRoomFuture.join();
        Member receiver = chatJoinRepository.findOtherMemberInChatRoom(chatRoomId, sender.getId())
                                            .orElseThrow(ChatJoinMemberNotFoundException::new);

        ChatMessage chatMessage = ChatMessage.builder()
                                             .chatRoom(chatRoom)
                                             .sender(sender)
                                             .receiver(receiver)
                                             .message(requestDto.getMessage())
                                             .threadId(requestDto.getThreadId())
                                             .build();
        chatMessageRepository.save(chatMessage);

        log.info("action = {}, email = {}, chatRoomId = {}, messageId = {}", "saveChatMessage",
                 email, chatRoomId, chatMessage.getId());
        return ChatMessageResponseDto.of(chatMessage, sender);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ChatRoomResponseDto> loadParticipatingChatRooms(String email, Pageable pageable) {
        return chatRoomRepository.fetchMyChatRoomList(email, pageable);
    }

    @Override
    public Page<ChatMessageResponseDto> loadMessagesByChatRoomId(String email, Long chatRoomId, Pageable pageable) {

        if (!chatJoinRepository.isValidChatJoiner(chatRoomId, email)) {
            throw new InvalidChatRoomAccessException();
        }

        Page<ChatMessageResponseDto> res = chatMessageRepository.getAllMessagesFromChatRoom(chatRoomId, pageable);
        for (ChatMessageResponseDto re : res) {
            System.out.println(re.getContent() + "     -> " + re.getTimestamp());
        }

        return res;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ChatRoomGetDto> getAllChatRoom(String email, Pageable pageable) {

        Member me = memberService.findMember(email);
        Long myId = me.getId();

        // 채팅방 리스트 담을 변수
        List<ChatRoomGetDto> list = new ArrayList<>();

        // 1. 채팅방 Id 리스트 (채팅방 나가기 했거나 채팅메세지가 하나도 없는 채팅방은 가져오지 X)
        //                                     => 마이브러리에서 pipi 누른다음에 메세지 안보내고 그냥 채팅방을 나온 경우

        // 페이지네이션 안한 코드
//        List<Long> chatRoomIdList2 = chatRoomRepository.chatRoomIdList(myId, page);

        // 페이지네이션 한 코드
        List<Long> chatRoomIdList = chatRoomRepository.chatRoomIdList2(myId, pageable)
                                                      .orElseThrow(ChatRoomNotFoundException::new);

        // 채팅방 리스트만큼 반복
        for (int i = 0; i < chatRoomIdList.size(); i++) {
            // 현재 채팅방 Id
            Long chatRoomId = chatRoomIdList.get(i);

            // 2. 현재 채팅방에 참여하고 있는 상대방 정보
            Member member = chatRoomRepository.findJoinMemberByChatRoomId(myId, chatRoomId);

            // 3. 현재 채팅방의 최근 메세지
            ChatMessage recentMessage = chatRoomRepository.findRecentMessage(chatRoomId);

            // 4. 읽지 않은 메세지 카운트 변수
            int count = 0;
            // 최근 메세지가 읽지 않았으면서 내가 보낸게 아닐 때 (= 상대방이 보낸걸 내가 읽지 않았을 때)
            if (!recentMessage.isRead() && !(recentMessage.getSender().getId().equals(myId))) {
                // 상대방이 보낸 내가 읽지 않은 메세지 카운트
                count = chatRoomRepository.countNotReadMessage(chatRoomId);
            }

            // 결과 변수에 담기
//            list.add(new ChatRoomGetDto(chatRoomId, joinMember, recentMessage.getMessage(),
//                                        recentMessage.getCreatedAt(), count));
        }

        return list;
    }

    @Override
    public void deleteChatRoom(String email, Long chatRoomId) {

        Member me = memberService.findMember(email);
        Long myId = me.getId();

        // 회원Id와 채팅방Id로 채팅참여 조회
        ChatJoin chatJoin = chatJoinRepository.findByChatJoin(myId, chatRoomId);
        // 나감여부 수정
        chatJoin.setExited(true);

    }

    @Override
    public Map<String, Object> getAllChatByChatRoomId(String email,
                                                      Long chatRoomId, Pageable page) {
        Member me = memberService.findMember(email);
        Long myId = me.getId();

        // 채팅 리스트 담을 변수
        List<TChatMessageGetDto> chatMessageList = new ArrayList<>();

        // 1. 채팅방의 메세지 리스트 조회
        List<ChatMessage> chatMessages = chatMessageRepository.getAllChatMessageByChatRoomId(chatRoomId, page);
//        List<ChatMessage> chatMessages = chatMessageRepository.getAllChatMessageByChatRoomId2(chatRoomId, page);
        for (ChatMessage chatMessage : chatMessages) {

            // 2. 상대방 정보
            Member you = chatJoinRepository.findOtherMemberInChatRoom(chatRoomId, myId)
                                           .orElseThrow(ChatJoinMemberNotFoundException::new);

            // 3. 스레드Id가 null이 아닐 때 스레드 조회
            ThreadShareGetDto thread = null;
            if (chatMessage.getThreadId() != null) {

                thread = threadRepository.getThreadShare(chatMessage.getThreadId());
            }

            // 4. sender가 상대방이고 읽음 여부가 false라면 true로 바꾸고 보내기
            if (chatMessage.getSender().getId().equals(you.getId()) && !chatMessage.isRead()) {
                chatMessage.setRead(true);
            }

            chatMessageList.add(new TChatMessageGetDto(chatMessage.getId(), you.getId(), chatMessage.getMessage(),
                                                       thread, chatMessage.isRead(), chatMessage.getCreatedAt()));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("chatRoomId", chatRoomId);
        map.put("chatMessageList", chatMessageList);
        return map;

    }

    @Override
    public Map<String, Object> getAllChatByMemberId(String email,
                                                    Long memberId, Pageable page) {

        Member me = memberService.findMember(email);
        Long myId = me.getId();

        // 1. 나와 상대방이 있는 채팅방이 존재하는지 (= 상대방과 한번이라도 채팅을 한적이 있는지)
        ChatJoin chatJoin = chatMessageRepository.isExistChatRoom(myId, memberId);
        if (chatJoin != null) {
            System.out.println("존재함");

            // 2. 해당 chatJoin 나감여부를 해제하기
            chatJoin.setExited(false);

            // 3. 존재하는 채팅방 Id
            Long chatRoomId = chatJoin.getChatRoom().getId();

            // 위의 메서드를 사용해서 채팅 리스트 구하기
            return getAllChatByChatRoomId(email, chatRoomId, page);

        } else {
            System.out.println("존재안함");

            // 2. 새로운 채팅방 생성
            ChatRoom chatRoom = ChatRoom.builder().build();
            chatRoomRepository.save(chatRoom);

            // 3. 새로 생성한 채팅방 Id
            Long chatRoomId = chatRoom.getId();

            // 4. 채팅참여 테이블에 나랑 상대방을 참여자로 하는 데이터 2개 생성하기
            // 4-1. 참여자 = 나
            ChatJoin chatJoin1 = new ChatJoin(1L, me, chatRoom, false);
            // 4-2. 참여자 = 상대방
            Member other = memberRepository.findById(memberId).get();
            ChatJoin chatJoin2 = new ChatJoin(1L, other, chatRoom, false);
            chatJoinRepository.save(chatJoin1);
            chatJoinRepository.save(chatJoin2);

            // 위의 메서드를 사용해서 채팅 리스트 구하기 (= 사실상 메세지는 없는 채팅 리스트)
            return getAllChatByChatRoomId(email, chatRoomId, page);
        }

    }

    @Override
    public Long createChat(String email, ChatMessagePostDto message) {

        // 보내는 회원 엔티티와 회원Id
        Member me = memberService.findMember(email);
        Long myId = me.getId();
        // 보내는 사람 객체 MemberInfoDto
        MemberInfoDto sender = memberRepository.getMemberInfo(myId).orElseThrow(MemberNotFoundException::new);

        // 채팅방 엔티티
        ChatRoom chatRoom = chatRoomRepository.findById(message.getChatRoomId()).get();

        // 받는 회원 엔티티
        Member receiver = memberRepository.findById(message.getReceiverId()).get();

        // 채팅메시지 엔티티
        ChatMessage newMessage = ChatMessage.builder()
                                            .chatRoom(chatRoom)
                                            .sender(me).receiver(receiver).message(message.getMessage())
                                            .threadId(null).isRead(false).build();

        // 채팅메시지 엔티티 저장 후 엔티티
        ChatMessage savedMessage = chatMessageRepository.save(newMessage);


        /*     웹소켓코드     */

        // 웹소켓 주소로 보내야할 객체 TChatMessageWebSocketGetDto
        TChatMessageWebSocketGetDto sendMessage = TChatMessageWebSocketGetDto.builder()
                                                                             .chatId(savedMessage.getId())
                                                                             .sender(sender)
                                                                             .message(savedMessage.getMessage())
                                                                             .thread(null)
                                                                             .isRead(savedMessage.isRead())
                                                                             .createdAt(savedMessage.getCreatedAt())
                                                                             .build();

        // 웹소켓 메서드
        String destination = "/sub/chat/" + receiver.getEmail(); // 구독 주소 + 받을 사람 이메일
        messagingTemplate.convertAndSend(destination, sendMessage); // destination으로 sendNotification을 보냄

        return savedMessage.getId();
    }

    @Override
    public Long threadShare(String email, ChatMessagePostDto message) {

        // 보내는 회원 엔티티와 회원Id
        Member me = memberService.findMember(email);
        Long myId = me.getId();
        // 보내는 사람 객체 MemberInfoDto
        MemberInfoDto sender = memberRepository.getMemberInfo(myId).orElseThrow(MemberNotFoundException::new);

        // 채팅방 엔티티
        ChatRoom chatRoom;

        // 채팅방id 없을 때
        if (message.getChatRoomId() == null) {
            chatRoom = chatRoomRepository.findChatRoom(myId, message.getReceiverId());
            // 둘이 함께한 채팅방이 없을 때
            if (chatRoom == null) {
                // 새로운 채팅방 생성
                chatRoom = ChatRoom.builder().build();
                chatRoomRepository.save(chatRoom);
            }
        } else {
            chatRoom = chatRoomRepository.findById(message.getChatRoomId()).orElseThrow(ChatRoomNotFoundException::new);
        }

        // 받는 회원 엔티티
        Member receiver = memberRepository.findById(message.getReceiverId()).orElseThrow(MemberNotFoundException::new);

        // 채팅 엔티티
        ChatMessage newMessage = ChatMessage.builder()
                                            .chatRoom(chatRoom)
                                            .sender(me).receiver(receiver).message(null)
                                            .threadId(message.getThreadId()).isRead(false).build();

        // 채팅메시지 엔티티 저장 후 엔티티
        ChatMessage savedMessage = chatMessageRepository.save(newMessage);


        /*     웹소켓코드     */

        // 스레드 객체 ThreadShareDto
        ThreadShareGetDto thread = threadRepository.getThreadShare(savedMessage.getThreadId());

        // 웹소켓 주소로 보내야할 객체 TChatMessageWebSocketGetDto
        TChatMessageWebSocketGetDto sendMessage = TChatMessageWebSocketGetDto.builder()
                                                                             .chatId(savedMessage.getId())
                                                                             .sender(sender)
                                                                             .message(null)
                                                                             .thread(thread)
                                                                             .isRead(savedMessage.isRead())
                                                                             .createdAt(savedMessage.getCreatedAt())
                                                                             .build();

        // 웹소켓 메서드
        String destination = "/sub/chat/" + receiver.getEmail(); // 구독 주소 + 받을 사람 이메일
        messagingTemplate.convertAndSend(destination, sendMessage); // destination으로 sendNotification을 보냄

        return savedMessage.getId();
    }

}
