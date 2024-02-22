package com.mybrary.backend.domain.member.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequestDto {

    @Email(message = "올바른 형식의 이메일 주소를 입력해 주십시오.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Schema(description = "이메일", example = "user1@ssafy.com")
    private String email;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Size(min = 3, max = 20, message = "비밀번호는 3자 이상 20자 이하이어야 합니다.")
    @Schema(description = "비밀번호", example = "Ssafy123!@")
    private String password;

}
