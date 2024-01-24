package com.mybrary.backend.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

    @Email(message = "유효한 이메일 주소를 입력해야 합니다.")
    @NotEmpty(message = "이메일은 비워둘 수 없습니다.")
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$])(?=.*\\d).{3,20}$",
             message = "비밀번호는 영문 대소문자, 숫자, 특수문자(!, @, #, $)를 조합하여 3~20자 이내여야 합니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 비워둘 수 없습니다.")
    private String passwordConfirm;

    @Pattern(regexp = "^([가-힇]){3,20}$",
             message = "이름은 한글(자음 또는 모음만 존재하는 것 제외)을 조합하여 3~20자 이내여야 합니다.")
    private String name;

    @Pattern(regexp = "^[a-zA-z0-9]{4,12}$",
             message = "닉네임은 영문자, 숫자 및 언더바(_)를 포함할 수 있으며 4~20자 이내여야 합니다.")
    private String nickname;

}
