package com.mybrary.backend.global.exception.member;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class MemberNotFoundException extends RuntimeException{
//    private final ErrorCode errorCode;

    public MemberNotFoundException(){

    }
}
