package com.example.semiwiki_backend.domain.auth.exception;

import com.example.semiwiki_backend.global.security.exception.ErrorCode;
import lombok.Getter;

@Getter
public class AccountAlreadyExistsException extends RuntimeException {

    private final ErrorCode errorCode;

  public AccountAlreadyExistsException() {
    super(ErrorCode.ACCOUNT_ALREADY_EXISTS.getMessage());
    this.errorCode = ErrorCode.ACCOUNT_ALREADY_EXISTS;
  }


}
