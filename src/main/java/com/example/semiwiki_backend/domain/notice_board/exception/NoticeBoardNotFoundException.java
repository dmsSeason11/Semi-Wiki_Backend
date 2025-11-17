package com.example.semiwiki_backend.domain.notice_board.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;
import com.example.semiwiki_backend.global.exception.SemiWikiException;

public class NoticeBoardNotFoundException extends SemiWikiException {

    public NoticeBoardNotFoundException() {
        super(ErrorCode.NOTICE_BOARD_NOT_FOUND);
    }
}
