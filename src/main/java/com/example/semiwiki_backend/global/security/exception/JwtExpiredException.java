package com.example.semiwiki_backend.global.security.exception;


import com.example.semiwiki_backend.global.exception.ErrorCode;



public class JwtExpiredException extends RuntimeException {

  private final ErrorCode errorCode;

  public JwtExpiredException() {
    super(ErrorCode.EXPIRED_TOKEN.getMessage());
    this.errorCode = ErrorCode.EXPIRED_TOKEN;
  }


}