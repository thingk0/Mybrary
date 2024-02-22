package com.mybrary.backend.global.exception.category;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class CategoryAccessDeniedException extends RuntimeException {

    private final ErrorCode errorCode;

    public CategoryAccessDeniedException() {
        this.errorCode = ErrorCode.CATEGORY_ACCESS_DENIED;
    }
}
