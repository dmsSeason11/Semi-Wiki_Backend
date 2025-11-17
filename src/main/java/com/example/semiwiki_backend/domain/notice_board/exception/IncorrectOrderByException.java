package com.example.semiwiki_backend.domain.notice_board.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;
import com.example.semiwiki_backend.global.exception.SemiWikiException;

public class IncorrectOrderByException extends SemiWikiException {
    public IncorrectOrderByException() {
        super(ErrorCode.INCORRECT_ORDER_BY);
    }
}
