package com.example.semiwiki_backend.domain.user_like.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;

public class NotLikedException extends RuntimeException {
    private final ErrorCode errorCode;

    public NotLikedException() {
        super(ErrorCode.NOT_LIKED.getMessage());
        errorCode = ErrorCode.NOT_LIKED;
    }
}
