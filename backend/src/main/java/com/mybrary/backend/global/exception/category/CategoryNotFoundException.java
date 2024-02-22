package com.mybrary.backend.global.exception.category;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class CategoryNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public CategoryNotFoundException() {
        this.errorCode = ErrorCode.CATEGORY_NOT_FOUND;
    }
}
