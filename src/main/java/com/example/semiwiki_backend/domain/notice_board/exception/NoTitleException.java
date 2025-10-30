package com.example.semiwiki_backend.domain.notice_board.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;
import com.example.semiwiki_backend.global.exception.SemiWikiException;

public class NoTitleException extends SemiWikiException {

    public NoTitleException() {
        super(ErrorCode.NO_TITLE);
    }
}
