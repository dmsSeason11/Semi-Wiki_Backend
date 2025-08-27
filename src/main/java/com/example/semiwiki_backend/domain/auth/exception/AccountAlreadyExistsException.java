package com.example.semiwiki_backend.domain.auth.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;



public class AccountAlreadyExistsException extends RuntimeException {

    private final ErrorCode errorCode;

  public AccountAlreadyExistsException() {
    super(ErrorCode.ACCOUNT_ALREADY_EXISTS.getMessage());
    this.errorCode = ErrorCode.ACCOUNT_ALREADY_EXISTS;
  }


}
