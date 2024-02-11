package com.mybrary.backend.global.exception.book;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class BookAccessDeniedException extends RuntimeException {

    private final ErrorCode errorCode;

    public BookAccessDeniedException() {
        this.errorCode = ErrorCode.BOOK_ACCESS_DENIED;
    }
}
