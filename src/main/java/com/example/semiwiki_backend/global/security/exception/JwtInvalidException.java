package com.example.semiwiki_backend.global.security.exception;

import lombok.Getter;

@Getter
public class JwtInvalidException extends RuntimeException {

    private final ErrorCode errorCode;

    public JwtInvalidException(){
        super(ErrorCode.INVALID_TOKEN.getMessage());
        this.errorCode = ErrorCode.INVALID_TOKEN;
    }


}
