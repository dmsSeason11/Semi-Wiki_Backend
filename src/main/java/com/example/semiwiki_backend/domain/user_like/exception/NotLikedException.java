package com.example.semiwiki_backend.domain.user_like.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;
import com.example.semiwiki_backend.global.exception.SemiWikiException;

public class NotLikedException extends SemiWikiException {

    public NotLikedException() {
        super(ErrorCode.NOT_LIKED);
    }
}
