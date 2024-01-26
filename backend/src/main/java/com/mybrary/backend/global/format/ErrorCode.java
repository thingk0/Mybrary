package com.mybrary.backend.global.format;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 글로벌 예외 처리
    GLOBAL_UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "처리 중 예기치 않은 서버 오류가 발생했습니다."),

    // 회원 관련 예외 처리
    MEMBER_SIGNUP_FAILED(HttpStatus.BAD_REQUEST, "회원 가입에 실패했습니다."),
    MEMBER_LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "로그인 정보가 정확하지 않습니다."),
    MEMBER_PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "입력하신 비밀번호가 일치하지 않습니다."),
    MEMBER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다.");


    private final HttpStatus status;
    private final String message;

}


