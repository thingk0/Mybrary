package com.mybrary.backend.domain.chat.service.impl;

import com.mybrary.backend.domain.chat.dto.ChatMessageGetDto;
import com.mybrary.backend.domain.chat.dto.ChatMessagePostDto;
import com.mybrary.backend.domain.chat.dto.ChatRoomGetDto;
import com.mybrary.backend.domain.chat.entity.ChatMessage;
import com.mybrary.backend.domain.chat.repository.ChatRepository;
import com.mybrary.backend.domain.chat.service.ChatService;
import com.mybrary.backend.domain.member.dto.MemberInfoDto;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.service.MemberService;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final MemberService memberService;

    @Override
    public List<ChatRoomGetDto> getAllChatRoom(Authentication authentication) {
        Member me = memberService.findMember(authentication.getName());
        Long myId = me.getId();
        List<ChatRoomGetDto> list = new ArrayList<>();

        List<Long> chatRoomIdList = chatRepository.chatRoomIdList(me.getId());

        for(int i = 0;i<chatRoomIdList.size();i++){
            Long chatRoomId = chatRoomIdList.get(i);

            Member member = chatRepository.findJoinMemberByChatRoomId(myId, chatRoomId);
            MemberInfoDto joinMember = new MemberInfoDto(member.getId(), member.getNickname(), member.getIntro(), null);

            ChatMessage recentMessage = chatRepository.findRecentMessage(chatRoomId);

            int count = 0;
            if(!recentMessage.isRead() && !(recentMessage.getSender().getId().equals(myId))){
                count = chatRepository.countNotReadMessage(chatRoomId);
            }

            list.add(new ChatRoomGetDto(chatRoomId, joinMember, recentMessage.getMessage(), recentMessage.getCreatedAt(), count));
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
        Member member = memberService.findMember(authentication.getName());
        return new ArrayList<>();
    }

    @Override
    public List<ChatMessageGetDto> getAllChatByMemberId(Authentication authentication,
                                                        Long memberId, Pageable page) {
        Member member = memberService.findMember(authentication.getName());
        return new ArrayList<>();
    }

    @Override
    public int createChat(Authentication authentication, ChatMessagePostDto message, Pageable page) {
        Member member = memberService.findMember(authentication.getName());
        return 1;
    }

}
