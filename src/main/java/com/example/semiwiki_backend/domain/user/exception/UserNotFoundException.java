package com.example.semiwiki_backend.domain.user.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;
import com.example.semiwiki_backend.global.exception.SemiWikiException;

public class UserNotFoundException extends SemiWikiException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
