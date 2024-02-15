package com.mybrary.backend.global.exception.book;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class BookNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public BookNotFoundException() {
        this.errorCode = ErrorCode.BOOK_NOT_FOUND;
    }
}
