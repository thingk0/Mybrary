package com.mybrary.backend.domain.member.dto;

import com.mybrary.backend.domain.image.dto.ImagePostDto;
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
public class MemberUpdateDto {

    /**
     *  회원정보 수정 요청
     */
    private Long memberId;
    private String intro;
    private String nickname;
    private boolean isProfilePublic;
    private boolean isNotifyEnable;

}
