package com.example.semiwiki_backend.domain.notice_board.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;

public class IncorrectOrderByException extends RuntimeException {
    private final ErrorCode errorCode;
    public IncorrectOrderByException() {
        super(ErrorCode.INCORRECT_ORDER_BY.getMessage());
        this.errorCode = ErrorCode.INCORRECT_ORDER_BY;
    }
}
