package com.example.semiwiki_backend.domain.auth.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;



public class StudentNumAlreadyExistsException extends RuntimeException {
    private final ErrorCode errorCode;
    public StudentNumAlreadyExistsException() {
        super(ErrorCode.STUDENT_NUM_ALREADY_EXISTS.getMessage());
        this.errorCode = ErrorCode.STUDENT_NUM_ALREADY_EXISTS;
    }
}
