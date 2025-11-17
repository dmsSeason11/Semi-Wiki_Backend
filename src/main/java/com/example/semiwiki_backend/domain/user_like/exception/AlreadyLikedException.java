package com.example.semiwiki_backend.domain.user_like.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;
import com.example.semiwiki_backend.global.exception.SemiWikiException;

public class AlreadyLikedException extends SemiWikiException {

    public AlreadyLikedException() {
        super(ErrorCode.ALREADY_LIKED);
    }
}
