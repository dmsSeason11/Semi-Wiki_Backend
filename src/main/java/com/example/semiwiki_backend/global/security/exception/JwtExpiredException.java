package com.example.semiwiki_backend.global.security.exception;


import lombok.Getter;

@Getter
public class JwtExpiredException extends RuntimeException {

  private final ErrorCode errorCode;

  public JwtExpiredException() {
    super(ErrorCode.EXPIRED_TOKEN.getMessage());
    this.errorCode = ErrorCode.EXPIRED_TOKEN;
  }


}