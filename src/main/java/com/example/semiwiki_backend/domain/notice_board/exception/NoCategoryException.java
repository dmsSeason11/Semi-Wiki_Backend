package com.example.semiwiki_backend.domain.notice_board.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;

public class NoCategoryException extends RuntimeException {

    private final ErrorCode errorCode;

    public NoCategoryException() {
        super(ErrorCode.NO_CATEGORY.getMessage());
        this.errorCode = ErrorCode.NO_CATEGORY;
    }
}
