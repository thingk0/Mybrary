package com.mybrary.backend.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberGetDto {

    /**
     *  회원 정보 조회
     *  MemberInfoDto와 이메일, 이름, 설졍 정보까지 들어있음
     */

    private Long memberId;
    private String email;
    private String name;
    private String nickname;
    private String intro;
    private String profileImageUrl;
    private boolean isProfilePublic;
    private boolean isNotifyEnabled;

}
