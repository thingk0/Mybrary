package com.mybrary.backend.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequestDto {

    @Email(message = "올바른 형식의 이메일 주소를 입력해 주십시오.")
    @NotEmpty(message = "이메일 필드는 필수 정보입니다. 공란으로 두실 수 없습니다.")
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$])(?=.*\\d).{3,20}$",
             message = "비밀번호는 영문 대소문자, 숫자, 특수문자(!, @, #, $)를 조합하여 3~20자 이내여야 합니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인란을 반드시 입력해 주셔야 합니다.")
    private String passwordConfirm;

    @Pattern(regexp = "^([가-힇]){3,20}$",
             message = "이름은 한글(자음 또는 모음만 존재하는 것 제외)을 조합하여 3~20자 이내여야 합니다.")
    private String name;

    @Pattern(regexp = "^[a-zA-z0-9]{4,12}$",
             message = "닉네임은 영문자, 숫자 및 언더바(_)를 포함할 수 있으며 4~20자 이내여야 합니다.")
    private String nickname;

    @Size(max = 50, message = "입력하신 내용이 허용된 길이를 초과합니다. 최대 50자 이내로 작성해주세요.")
    private String intro;

}
