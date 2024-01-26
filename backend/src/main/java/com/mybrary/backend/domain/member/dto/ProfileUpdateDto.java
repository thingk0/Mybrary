package com.mybrary.backend.domain.member.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileUpdateDto {

    /**
     *  회원정보 수정 요청
     */
    private Long memberId;
    private String intro;
    private String nickname;

}
