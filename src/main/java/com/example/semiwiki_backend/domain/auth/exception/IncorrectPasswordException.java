package com.example.semiwiki_backend.domain.auth.exception;

import com.example.semiwiki_backend.global.security.exception.ErrorCode;

public class IncorrectPasswordException extends RuntimeException {
  public static final RuntimeException EXCEPTION = new IncorrectPasswordException();
  private IncorrectPasswordException() {
    super(ErrorCode.INCORRECT_PASSWORD.getMessage());
  }

}
