package com.mybrary.backend.domain.member.dto;

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
public class MemberUpdateDto {

    /**
     *  회원정보 수정 요청
     */

    private Long memberId;
    private String nickname;
    private String intro;
    private String profileImageUrl;

}
