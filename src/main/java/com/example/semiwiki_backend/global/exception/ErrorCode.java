package com.example.semiwiki_backend.global.exception;

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
    INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED,"incorrect password"),
    NO_CATEGORY(HttpStatus.BAD_REQUEST,"there's no category"),
    NOTICE_BOARD_NOT_FOUND(HttpStatus.NOT_FOUND,"notice board not found"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"user not found"),
    ALREADY_LIKED(HttpStatus.BAD_REQUEST,"already_liked"),
    NOT_LIKED(HttpStatus.BAD_REQUEST,"not liked"),
    STUDENT_NUM_ALREADY_EXISTS(HttpStatus.CONFLICT,"student number already exists"),
    HEADER_NOT_FOUND(HttpStatus.NOT_FOUND,"header not found"),
    NO_HEADER(HttpStatus.BAD_REQUEST,"no header");


    private final HttpStatus httpStatus;
    private final String message;
}