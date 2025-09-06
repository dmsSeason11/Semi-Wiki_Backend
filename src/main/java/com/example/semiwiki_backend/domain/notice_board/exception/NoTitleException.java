package com.example.semiwiki_backend.domain.notice_board.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;

public class NoTitleException extends RuntimeException {
    private final ErrorCode errorCode;

    public NoTitleException() {
        super(ErrorCode.NO_TITLE.getMessage());
        this.errorCode = ErrorCode.NO_TITLE;
    }
}
