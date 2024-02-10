package com.mybrary.backend.global.exception.bookshelf;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class BookshelfNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public BookshelfNotFoundException() {
        this.errorCode = ErrorCode.BOOK_SHELF_NOT_FOUND;
    }
}
