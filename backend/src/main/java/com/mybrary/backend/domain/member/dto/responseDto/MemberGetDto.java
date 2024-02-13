package com.mybrary.backend.domain.member.dto.responseDto;

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
    private Long profileImageId;
    private String profileImageUrl;
    private boolean isProfilePublic;
    private boolean isNotifyEnabled;
    private int followStatus;

    public MemberGetDto(Long memberId, String email, String name, String nickname, String intro, Long profileImageId,
                        String profileImageUrl, boolean isProfilePublic, boolean isNotifyEnabled) {
        this.memberId = memberId;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.intro = intro;
        this.profileImageId = profileImageId;
        this.profileImageUrl = profileImageUrl;
        this.isProfilePublic = isProfilePublic;
        this.isNotifyEnabled = isNotifyEnabled;
    }
}
