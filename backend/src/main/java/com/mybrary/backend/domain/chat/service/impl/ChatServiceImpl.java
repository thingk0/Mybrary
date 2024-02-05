package com.mybrary.backend.domain.chat.service.impl;

import com.mybrary.backend.domain.chat.dto.*;
import com.mybrary.backend.domain.chat.entity.*;
import com.mybrary.backend.domain.chat.repository.*;
import com.mybrary.backend.domain.chat.service.ChatService;
import com.mybrary.backend.domain.contents.thread.dto.ThreadSimpleGetDto;
import com.mybrary.backend.domain.member.dto.MemberInfoDto;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import com.mybrary.backend.domain.member.service.MemberService;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatJoinRepository chatJoinRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;


    @Transactional(readOnly = true)
    @Override
    public List<ChatRoomGetDto> getAllChatRoom(Long myId) {

        // 채팅방 리스트 담을 변수
        List<ChatRoomGetDto> list = new ArrayList<>();

        // 1. 채팅방 Id 리스트 (채팅방 나가기 했거나 채팅메세지가 하나도 없는 채팅방은 가져오지 X)
        //                                     => 마이브러리에서 pipi 누른다음에 메세지 안보내고 그냥 채팅방을 나온 경우
        List<Long> chatRoomIdList = chatRoomRepository.chatRoomIdList(myId);

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
    public void deleteChatRoom(Long myId, Long chatRoomId) {
        // 회원Id와 채팅방Id로 채팅참여 조회
        ChatJoin chatJoin = chatJoinRepository.findByChatJoin(myId, chatRoomId);
        // 나감여부 수정
        chatJoin.setExited(true);

    }

    @Override
    public List<ChatMessageGetDto> getAllChatByChatRoomId(Long myId,
                                                          Long chatRoomId, Pageable page) {

        // 채팅 리스트 담을 변수
        List<ChatMessageGetDto> chatMessageList = new ArrayList<>();

        // 1. 채팅방의 메세지 리스트 조회
        System.out.println("채팅리스트");
        List<ChatMessage> chatMessages = chatMessageRepository.getAllChatMessageByChatRoomId(chatRoomId);
        for (ChatMessage chatMessage : chatMessages) {

            // 2. 상대방 정보
            Member member = chatMessageRepository.getJoinMemberByMemberId(chatMessage.getSender().getId());
            MemberInfoDto joinMember = new MemberInfoDto(member.getId(), member.getNickname(),
                                                         member.getIntro(), null);

            // 3. 스레드Id가 null이 아닐 때 스레드 조회
            ThreadSimpleGetDto thread = new ThreadSimpleGetDto();
            if (chatMessage.getThreadId() != null) {
                /* 스레드 간단 조회하는 거 추가해야함 */
            }

            // 4. sender가 상대방이고 읽음 여부가 false라면 true로 바꾸고 보내기
            if (chatMessage.getSender().getId().equals(member.getId()) && !chatMessage.isRead()) {
                chatMessage.setRead(true);
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
    public List<ChatMessageGetDto> getAllChatByMemberId(Long myId,
                                                        Long memberId, Pageable page) {
        // 1. 나와 상대방이 있는 채팅방이 존재하는지 (= 상대방과 한번이라도 채팅을 한적이 있는지)
        ChatJoin chatJoin = chatMessageRepository.isExistChatRoom(myId, memberId);
        if (chatJoin != null) {
            System.out.println("존재함");

            // 2. 해당 chatJoin 나감여부를 해제하기
            chatJoin.setExited(false);

            // 3. 존재하는 채팅방 Id
            Long chatRoomId = chatJoin.getChatRoom().getId();

            // 위의 메서드를 사용해서 채팅 리스트 구하기
            return getAllChatByChatRoomId(myId, chatRoomId, page);
        } else {
            System.out.println("존재안함");

            // 2. 새로운 채팅방 생성
            ChatRoom chatRoom = ChatRoom.builder().build();
            chatRoomRepository.save(chatRoom);

            // 3. 새로 생성한 채팅방 Id
            Long chatRoomId = chatRoom.getId();

            // 4. 채팅참여 테이블에 나랑 상대방을 참여자로 하는 데이터 2개 생성하기
            // 4-1. 참여자 = 나
            Member me = memberRepository.findById(myId).get();
            ChatJoin chatJoin1 = new ChatJoin(1L, me, chatRoom, false);
            // 4-2. 참여자 = 상대방
            Member other = memberRepository.findById(memberId).get();
            ChatJoin chatJoin2 = new ChatJoin(1L, other, chatRoom, false);
            chatJoinRepository.save(chatJoin1);
            chatJoinRepository.save(chatJoin2);

            // 위의 메서드를 사용해서 채팅 리스트 구하기 (= 사실상 메세지는 없는 채팅 리스트)
            return getAllChatByChatRoomId(myId, chatRoomId, page);
        }

    }

    @Override
    public void createChat(Long myId, ChatMessagePostDto message) {

        ChatRoom chatRoom = chatRoomRepository.findById(message.getChatRoomId()).get();
        Member me = memberRepository.findById(myId).get();
        Member receiver = memberRepository.findById(message.getReceiverId()).get();

        ChatMessage newMessage = ChatMessage.builder()
                                            .chatRoom(chatRoom)
                                            .sender(me).receiver(receiver).message(message.getMessage())
                                            .threadId(null).isRead(false).build();

        ChatMessage savedMessage = chatMessageRepository.save(newMessage);

    }

    @Override
    public void threadShare(Long myId, ChatMessagePostDto message) {

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
            chatRoom = chatRoomRepository.findById(message.getChatRoomId()).get();
        }

        Member me = memberRepository.findById(myId).get();
        Member receiver = memberRepository.findById(message.getReceiverId()).get();

        ChatMessage newMessage = ChatMessage.builder()
                                            .chatRoom(chatRoom)
                                            .sender(me).receiver(receiver).message(null)
                                            .threadId(message.getThreadId()).isRead(false).build();

        ChatMessage savedMessage = chatMessageRepository.save(newMessage);
    }

}
