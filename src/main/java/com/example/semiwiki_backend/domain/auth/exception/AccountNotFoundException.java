package com.example.semiwiki_backend.domain.auth.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;
import com.example.semiwiki_backend.global.exception.SemiWikiException;


public class AccountNotFoundException extends SemiWikiException {


    public AccountNotFoundException() {
        super(ErrorCode.ACCOUNT_NOT_FOUND);
    }


}
