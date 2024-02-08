package com.mybrary.backend.global.exception.book;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class BookCreateException extends RuntimeException {

    private final ErrorCode errorCode;

    public BookCreateException() {this.errorCode = ErrorCode.BOOK_CREATE_FAILED; }
}
