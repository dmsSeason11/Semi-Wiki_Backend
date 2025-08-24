package com.example.semiwiki_backend.domain.auth.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;



public class AccountNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public AccountNotFoundException() {
        super(ErrorCode.ACCOUNT_NOT_FOUND.getMessage());
        this.errorCode = ErrorCode.ACCOUNT_NOT_FOUND;
    }


}
