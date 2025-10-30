package com.example.semiwiki_backend.domain.auth.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;
import com.example.semiwiki_backend.global.exception.SemiWikiException;


public class AccountAlreadyExistsException extends SemiWikiException {

  public AccountAlreadyExistsException() {
    super(ErrorCode.ACCOUNT_ALREADY_EXISTS);
  }


}
