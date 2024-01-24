package com.mybrary.backend.global.format;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {

    /* 회원(Member) */
    MEMBER_SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입이 정상적으로 완료되었습니다."),
    MEMBER_LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공하였습니다."),
    MEMBER_LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃이 정상적으로 이루어졌습니다."),
    MEMBER_ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."),
    MEMBER_INFO_UPDATE_SUCCESS(HttpStatus.OK, "회원 정보가 성공적으로 업데이트되었습니다."),
    MEMBER_DELETED(HttpStatus.OK, "회원 정보가 성공적으로 삭제되었습니다.");

    private final HttpStatus status;
    private final String message;

}

