package com.mybrary.backend.domain.member.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
    @Schema(description = "이메일", example = "user1@ssafy.com")
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*\\d).{8,20}$",
             message = "비밀번호는 영문 대소문자, 숫자, 특수문자(!, @, #, $, %, ^, &, *)를 조합하여 3~20자 이내여야 합니다.")
    @Schema(description = "비밀번호", example = "Ssafy123!@")
    private String password;


    @NotEmpty(message = "비밀번호 확인란을 반드시 입력해 주셔야 합니다.")
    @Schema(description = "비밀번호 확인", example = "Ssafy123!@")
    private String passwordConfirm;


    @Pattern(regexp = "^([가-힇]){2,5}$",
             message = "이름은 한글(자음 또는 모음만 존재하는 것 제외)을 조합하여 2~5자 이내여야 합니다.")
    @Schema(description = "이름", example = "김싸피")
    private String name;


    @Pattern(regexp = "^[a-zA-Z0-9_]{3,10}$",
             message = "닉네임은 영문자, 숫자 및 언더바(_)를 포함할 수 있으며 3~10자 이내여야 합니다.")
    @Schema(description = "닉네임", example = "MyBrary_2024")
    private String nickname;

}
