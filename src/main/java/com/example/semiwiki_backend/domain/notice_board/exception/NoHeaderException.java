package com.example.semiwiki_backend.domain.notice_board.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;

public class NoHeaderException extends RuntimeException {
    private final ErrorCode errorCode;
    public NoHeaderException() {
        super(ErrorCode.NO_HEADER.getMessage());
        this.errorCode = ErrorCode.NO_HEADER;
    }
}
