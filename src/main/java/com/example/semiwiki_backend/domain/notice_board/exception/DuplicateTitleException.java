package com.example.semiwiki_backend.domain.notice_board.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;

public class DuplicateTitleException extends RuntimeException {
  private final ErrorCode errorCode;
  public DuplicateTitleException() {
    super(ErrorCode.DUPLICATE_TITLE.getMessage());
    this.errorCode = ErrorCode.DUPLICATE_TITLE;

  }
}
