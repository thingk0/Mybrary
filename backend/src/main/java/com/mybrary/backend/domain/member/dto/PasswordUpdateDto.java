package com.mybrary.backend.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    /**
     *  비밀번호 수정 요청
     */

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*\\d).{8,20}$",
             message = "비밀번호는 영문 대소문자, 숫자, 특수문자(!, @, #, $, %, ^, &, *)를 조합하여 3~20자 이내여야 합니다.")
    private String password;


    @NotEmpty(message = "비밀번호 확인란을 반드시 입력해 주셔야 합니다.")
    private String passwordConfirm;

}
