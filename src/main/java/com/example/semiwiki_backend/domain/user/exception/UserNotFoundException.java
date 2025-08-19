package com.example.semiwiki_backend.domain.user.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;

public class UserNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND.getMessage());
        this.errorCode = ErrorCode.USER_NOT_FOUND;
    }
}
