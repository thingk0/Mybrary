package com.mybrary.backend.domain.chat.dto.responseDto;

import com.mybrary.backend.domain.member.dto.responseDto.MemberInfoDto;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomGetDto {

    /**
     * 채팅방 목록 조회 최근메세지의 시간순으로 채팅방목록을 정렬해야하기 때문에 recentMessageTime 포함
     */

    private Long chatRoomId;
    private String recentMessage;
    private MemberInfoDto joinMember;

    private int newMessageCount;
    private LocalDateTime recentMessageTime;

    public ChatRoomGetDto(Long chatRoomId, MemberInfoDto joinMember) {
        this.chatRoomId = chatRoomId;
        this.joinMember = joinMember;
    }

}
