package com.example.semiwiki_backend.domain.auth.exception;

import com.example.semiwiki_backend.global.security.exception.ErrorCode;
import lombok.Getter;

@Getter
public class IncorrectPasswordException extends RuntimeException {

  private final ErrorCode errorCode;

  public IncorrectPasswordException() {
    super(ErrorCode.INCORRECT_PASSWORD.getMessage());
    this.errorCode = ErrorCode.INCORRECT_PASSWORD;
  }

}
