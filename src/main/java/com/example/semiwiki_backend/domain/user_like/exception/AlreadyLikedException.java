package com.example.semiwiki_backend.domain.user_like.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;

public class AlreadyLikedException extends RuntimeException {
    private final ErrorCode errorCode;

    public AlreadyLikedException() {
        super(ErrorCode.ALREADY_LIKED.getMessage());
        errorCode = ErrorCode.ALREADY_LIKED;
    }
}
