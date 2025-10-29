package com.example.semiwiki_backend.global.exception;

import lombok.Getter;

@Getter
public class SemiWikiException extends RuntimeException {

  private final ErrorCode errorCode;

  public SemiWikiException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
