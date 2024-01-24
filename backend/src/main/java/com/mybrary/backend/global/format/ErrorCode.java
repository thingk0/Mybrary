package com.mybrary.backend.global.format;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /* 글로벌 예외 */
    UNEXPECTED(HttpStatus.INTERNAL_SERVER_ERROR, "서버 처리 중 예기치 못한 오류가 발생하였습니다."),

    /* 회원(Member) 예외 */
    MEMBER_SIGNUP_FAIL(HttpStatus.BAD_REQUEST, "회원가입에 실패하였습니다."),
    MEMBER_LOGIN_FAIL(HttpStatus.UNAUTHORIZED, "로그인 정보가 올바르지 않습니다.");

    private final HttpStatus status;
    private final String message;

}


