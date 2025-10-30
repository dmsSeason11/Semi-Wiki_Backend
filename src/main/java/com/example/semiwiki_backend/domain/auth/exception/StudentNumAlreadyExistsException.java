package com.example.semiwiki_backend.domain.auth.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;
import com.example.semiwiki_backend.global.exception.SemiWikiException;


public class StudentNumAlreadyExistsException extends SemiWikiException {
    public StudentNumAlreadyExistsException() {
        super(ErrorCode.STUDENT_NUM_ALREADY_EXISTS);
    }
}
