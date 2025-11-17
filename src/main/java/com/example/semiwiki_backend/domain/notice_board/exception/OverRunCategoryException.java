package com.example.semiwiki_backend.domain.notice_board.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;
import com.example.semiwiki_backend.global.exception.SemiWikiException;

public class OverRunCategoryException extends SemiWikiException {
    public OverRunCategoryException() {
        super(ErrorCode.OVER_RUN_CATEGORY);
    }
}
