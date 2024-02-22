package com.mybrary.backend.global.exception.book;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class BookDeleteException extends RuntimeException {

    private final ErrorCode errorCode;

    public BookDeleteException() {this.errorCode = ErrorCode.BOOK_DELETE_FAILED; }
}
