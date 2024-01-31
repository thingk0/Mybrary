package com.mybrary.backend.domain.chat.service.impl;

import com.mybrary.backend.domain.chat.dto.ChatMessageGetDto;
import com.mybrary.backend.domain.chat.dto.ChatMessagePostDto;
import com.mybrary.backend.domain.chat.dto.ChatRoomGetDto;
import com.mybrary.backend.domain.chat.entity.ChatJoin;
import com.mybrary.backend.domain.chat.entity.ChatMessage;
import com.mybrary.backend.domain.chat.entity.ChatRoom;
import com.mybrary.backend.domain.chat.repository.ChatJoinRepository;
import com.mybrary.backend.domain.chat.repository.ChatMessageRepository;
import com.mybrary.backend.domain.chat.repository.ChatRoomRepository;
import com.mybrary.backend.domain.chat.service.ChatService;
import com.mybrary.backend.domain.contents.thread.dto.ThreadSimpleGetDto;
import com.mybrary.backend.domain.member.dto.MemberInfoDto;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.service.MemberService;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatJoinRepository chatJoinRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberService memberService;

    @Override
    public List<ChatRoomGetDto> getAllChatRoom(Authentication authentication) {
        // 로그인한 회원 정보
        Member me = memberService.findMember(authentication.getName());
        Long myId = me.getId();

        // 채팅방 리스트 담을 변수
        List<ChatRoomGetDto> list = new ArrayList<>();

        // 1. 채팅방 Id 리스트
        List<Long> chatRoomIdList = chatRoomRepository.chatRoomIdList(me.getId());

        // 채팅방 리스트만큼 반복
        for (int i = 0; i < chatRoomIdList.size(); i++) {
            // 현재 채팅방 Id
            Long chatRoomId = chatRoomIdList.get(i);

            // 2. 현재 채팅방에 참여하고 있는 상대방 정보
            Member member = chatRoomRepository.findJoinMemberByChatRoomId(myId, chatRoomId);
            MemberInfoDto joinMember = new MemberInfoDto(member.getId(), member.getNickname(),
                                                         member.getIntro(), null);

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
            list.add(new ChatRoomGetDto(chatRoomId, joinMember, recentMessage.getMessage(),
                                        recentMessage.getCreatedAt(), count));
        }

        return list;
    }

    @Override
    public void deleteChatRoom(Authentication authentication, Long chatRoomId) {
        Member member = memberService.findMember(authentication.getName());

    }

    @Override
    public List<ChatMessageGetDto> getAllChatByChatRoomId(Authentication authentication,
                                                          Long chatRoomId, Pageable page) {
        // 로그인한 회원 정보
        Member me = memberService.findMember(authentication.getName());
        Long myId = me.getId();

        // 채팅 리스트 담을 변수
        List<ChatMessageGetDto> chatMessageList = new ArrayList<>();

        // 1. 채팅방의 메세지 리스트 조회
        System.out.println("채팅리스트");
        List<ChatMessage> chatMessages = chatMessageRepository.getAllChatMessageByChatRoomId(chatRoomId);
        for (ChatMessage chatMessage : chatMessages) {
            System.out.println(
                "message : " + chatMessage.getMessage() + " / " + chatMessage.getCreatedAt());

            // 2. 상대방 정보
            Member member = chatMessageRepository.getJoinMemberByMemberId(chatMessage.getSender().getId());
            MemberInfoDto joinMember = new MemberInfoDto(member.getId(), member.getNickname(),
                                                         member.getIntro(), null);

            // 3. 스레드Id가 null이 아닐 때 스레드 조회
            ThreadSimpleGetDto thread = new ThreadSimpleGetDto();
            if (chatMessage.getThreadId() != null) {
                /* 스레드 간단 조회하는 거 추가해야함 */
            }
            chatMessageList
                .add(
                    new ChatMessageGetDto(chatMessage.getId(), joinMember, chatMessage.getMessage(),
                                          thread, chatMessage.isRead(),
                                          chatMessage.getCreatedAt()));
        }

        return chatMessageList;
    }

    @Override
    public List<ChatMessageGetDto> getAllChatByMemberId(Authentication authentication,
                                                        Long memberId, Pageable page) {
        // 로그인한 회원 정보
        Member me = memberService.findMember(authentication.getName());
        Long myId = me.getId();

        // 1. 나와 상대방이 있는 채팅방이 존재하는지 (= 상대방과 한번이라도 채팅을 한적이 있는지)
        ChatJoin chatJoin = chatMessageRepository.isExistChatRoom(myId, memberId);
        if(chatJoin != null){
            System.out.println("존재함");

            // 2. 존재하는 채팅방 Id
            Long chatRoomId = chatJoin.getChatRoom().getId();

            // 위의 메서드를 사용해서 채팅 리스트 구하기
            return getAllChatByChatRoomId(authentication, chatRoomId, page);
        } else{
            System.out.println("존재안함");

            // 2. 새로운 채팅방 생성
            ChatRoom chatRoom = new ChatRoom();
            chatRoomRepository.save(chatRoom);

            // 3. 새로 생성한 채팅방 Id
            Long chatRoomId = chatRoom.getId();

            // 위의 메서드를 사용해서 채팅 리스트 구하기 (= 사실상 메세지는 없는 채팅 리스트)
            return getAllChatByChatRoomId(authentication, chatRoomId, page);
        }

    }

    @Override
    public int createChat(Authentication authentication, ChatMessagePostDto message,
                          Pageable page) {
        Member member = memberService.findMember(authentication.getName());
        return 1;
    }

}
