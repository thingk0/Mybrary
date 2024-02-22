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
public class MemberInfoDto {

    /**
     *  회원 정보 간단 조회
     *  프로필이미지, 닉네임, 간단소개만 들어있음
     *  (이메일, 이름 등은 목업을 보니 표시할 일이 없어서 따로 만들었음)
     */

    private Long memberId;
    private String nickname;
    private String intro;
    private Long imageId;
    private String imageUrl;

    public MemberInfoDto(Long memberId) {
        this.memberId = memberId;
    }
}
