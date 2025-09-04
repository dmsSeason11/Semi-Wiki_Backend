package com.example.semiwiki_backend.domain.notice_board.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;

public class OverRunCategoryException extends RuntimeException {
    private final ErrorCode errorCode;
    public OverRunCategoryException() {
        super(ErrorCode.OVER_RUN_CATEGORY.getMessage());
        this.errorCode = ErrorCode.OVER_RUN_CATEGORY;
    }
}
