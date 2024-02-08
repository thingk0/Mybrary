package com.mybrary.backend.global.exception.category;

import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.Getter;

@Getter
public class CategoryOwnerNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public CategoryOwnerNotFoundException() {
        this.errorCode = ErrorCode.CATEGORY_OWNER_NOT_FOUND;
    }
}
