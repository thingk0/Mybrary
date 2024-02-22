package com.mybrary.backend.global.exception.book;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class BookUpdateException extends RuntimeException {

    private final ErrorCode errorCode;

    public BookUpdateException() {this.errorCode = ErrorCode.BOOK_UPDATE_FAILED; }
}
