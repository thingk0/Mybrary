package com.mybrary.backend.domain.member.dto;

import com.mybrary.backend.domain.image.dto.ImagePostDto;
import jakarta.validation.constraints.Pattern;
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

    @Pattern(regexp = "^[a-zA-Z0-9_]{3,15}$",
             message = "닉네임은 영문자, 숫자 및 언더바(_)를 포함할 수 있으며 3~15자 이내여야 합니다.")
    private String nickname;

    private boolean isProfilePublic;
    private boolean isNotifyEnable;

}
