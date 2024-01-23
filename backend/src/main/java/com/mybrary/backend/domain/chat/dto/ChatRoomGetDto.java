package com.mybrary.backend.domain.chat.dto;

import com.mybrary.backend.domain.member.dto.MemberGetDto;
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

    private Long chatRoomId;
    private MemberGetDto joinMember;
    private String recentMessage;
    private int newMessageCount;

}
