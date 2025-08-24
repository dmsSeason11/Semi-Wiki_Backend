package com.example.semiwiki_backend.domain.notice_board.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;

public class HeaderNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public HeaderNotFoundException() {
        super(ErrorCode.HEADER_NOT_FOUND.getMessage());
        this.errorCode = ErrorCode.HEADER_NOT_FOUND;
    }
}
