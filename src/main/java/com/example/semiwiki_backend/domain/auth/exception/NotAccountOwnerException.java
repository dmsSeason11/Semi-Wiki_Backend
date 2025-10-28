package com.example.semiwiki_backend.domain.auth.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;

public class NotAccountOwnerException extends RuntimeException {

  private final ErrorCode errorCode;
  public NotAccountOwnerException() {
    super(ErrorCode.NOT_ACCOUNT_OWNER.getMessage());
    this.errorCode = ErrorCode.NOT_ACCOUNT_OWNER;
  }
}
