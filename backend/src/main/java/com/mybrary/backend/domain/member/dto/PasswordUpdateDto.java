package com.mybrary.backend.domain.member.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
public class PasswordUpdateDto {

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$])(?=.*\\d).{3,20}$",
             message = "비밀번호는 영문 대소문자, 숫자, 특수문자(!, @, #, $)를 조합하여 3~20자 이내여야 합니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 비워둘 수 없습니다.")
    private String passwordConfirm;

}
