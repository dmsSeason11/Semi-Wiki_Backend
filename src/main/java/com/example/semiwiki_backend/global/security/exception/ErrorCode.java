package com.example.semiwiki_backend.global.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EXPIRED_TOKEN(HttpStatus.FORBIDDEN,"token is expired"),
    INVALID_TOKEN(HttpStatus.FORBIDDEN,"token is invalid"),
    ACCOUNT_ALREADY_EXISTS(HttpStatus.CONFLICT,"account already exists"),
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND,"account not found"),
    INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED,"incorrect password");

    private final HttpStatus httpStatus;
    private final String message;
}
