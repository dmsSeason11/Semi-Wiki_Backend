package com.example.semiwiki_backend.global.security.exception;

public class JwtInvalidException extends RuntimeException {

    //이런식으로 예외 처리를 하면 객체가 한번망 생성됨
    public static final RuntimeException EXCEPTION= new JwtInvalidException();
    private JwtInvalidException(){
        super(ErrorCode.INVALID_TOKEN.getMessage());
    }
}
