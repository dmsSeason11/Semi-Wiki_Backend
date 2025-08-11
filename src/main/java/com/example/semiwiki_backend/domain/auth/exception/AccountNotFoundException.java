package com.example.semiwiki_backend.domain.auth.exception;

import com.example.semiwiki_backend.global.security.exception.ErrorCode;

public class AccountNotFoundException extends RuntimeException {
    public static final RuntimeException EXCEPTION = new AccountNotFoundException();
    private AccountNotFoundException() {
        super(ErrorCode.ACCOUNT_NOT_FOUND.getMessage());
    }

}
