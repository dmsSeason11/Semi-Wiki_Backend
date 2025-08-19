package com.example.semiwiki_backend.domain.notice_board.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;

public class NoticeBoardNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
    public NoticeBoardNotFoundException() {
        super(ErrorCode.NOTICE_BOARD_NOT_FOUND.getMessage());
        this.errorCode = ErrorCode.NOTICE_BOARD_NOT_FOUND;
    }
}
