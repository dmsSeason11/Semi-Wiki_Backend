package com.example.semiwiki_backend.domain.auth.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;
import com.example.semiwiki_backend.global.exception.SemiWikiException;


public class IncorrectPasswordException extends SemiWikiException {


  public IncorrectPasswordException() {
    super(ErrorCode.INCORRECT_PASSWORD);
  }

}
