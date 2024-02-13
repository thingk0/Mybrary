package com.mybrary.backend.domain.member.dto.requestDto;

import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
    private Long profileImageId;

    @Pattern(regexp = "^[a-zA-Z0-9_]{3,10}$",
             message = "닉네임은 영문자, 숫자 및 언더바(_)를 포함할 수 있으며 3~10자 이내여야 합니다.")
    private String nickname;
    private String intro;

    private boolean isProfilePublic;
    private boolean isNotifyEnable;

}
