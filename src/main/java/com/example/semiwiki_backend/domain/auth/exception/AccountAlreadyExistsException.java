package com.example.semiwiki_backend.domain.auth.exception;

import com.example.semiwiki_backend.global.security.exception.ErrorCode;
import com.example.semiwiki_backend.global.security.exception.JwtExpiredException;

public class AccountAlreadyExistsException extends RuntimeException {
  public static final RuntimeException EXCEPTION = new AccountAlreadyExistsException();
  private AccountAlreadyExistsException() {
    super(ErrorCode.ACCOUNT_ALREADY_EXISTS.getMessage());
  }
}
