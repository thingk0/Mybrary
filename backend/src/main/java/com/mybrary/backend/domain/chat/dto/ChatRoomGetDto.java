package com.mybrary.backend.domain.chat.dto;

import com.mybrary.backend.domain.member.dto.MemberInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomGetDto {

    /**
     *  채팅방 목록 조회
     *  최근메세지의 시간순으로 채팅방목록을 정렬해야하기 때문에 recentMessageTime 포함
     */

    private Long chatRoomId;
    private MemberInfoDto joinMember;
    private String recentMessage;
    private String recentMessageTime;
    private int newMessageCount;

}
