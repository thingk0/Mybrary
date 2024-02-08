package com.mybrary.backend.global.exception.book;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class BookAlreadySubscribeException extends RuntimeException {

    private final ErrorCode errorCode;

    public BookAlreadySubscribeException() {this.errorCode = ErrorCode.BOOK_ALREADY_SUBSCRIBE; }
}
