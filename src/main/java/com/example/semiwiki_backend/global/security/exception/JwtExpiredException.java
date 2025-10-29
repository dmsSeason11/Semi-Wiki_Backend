package com.example.semiwiki_backend.global.security.exception;


import com.example.semiwiki_backend.global.exception.ErrorCode;
import com.example.semiwiki_backend.global.exception.SemiWikiException;
import lombok.Getter;

@Getter
public class JwtExpiredException extends SemiWikiException {

  public JwtExpiredException() {
    super(ErrorCode.EXPIRED_TOKEN);
  }


}