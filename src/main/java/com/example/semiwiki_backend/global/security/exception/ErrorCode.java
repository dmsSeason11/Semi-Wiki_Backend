package com.example.semiwiki_backend.global.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EXPIRED_TOKEN(HttpStatus.FORBIDDEN,"token is expired"),
    INVALID_TOKEN(HttpStatus.FORBIDDEN,"token is invalid");



    private final HttpStatus httpStatus;
    private final String message;
}
