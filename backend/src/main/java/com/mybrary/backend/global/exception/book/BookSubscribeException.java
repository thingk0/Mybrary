package com.mybrary.backend.global.exception.book;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class BookSubscribeException extends RuntimeException {

    private final ErrorCode errorCode;

    public BookSubscribeException() {this.errorCode = ErrorCode.BOOK_SUBSCRIBE_FAILED; }
}
