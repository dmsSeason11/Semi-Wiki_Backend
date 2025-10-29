package com.example.semiwiki_backend.domain.notice_board.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;
import com.example.semiwiki_backend.global.exception.SemiWikiException;

public class DuplicateTitleException extends SemiWikiException {
  public DuplicateTitleException() {
    super(ErrorCode.DUPLICATE_TITLE);

  }
}
