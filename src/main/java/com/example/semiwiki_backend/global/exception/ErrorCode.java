package com.example.semiwiki_backend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED,"token is expired"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,"token is invalid"),
    ACCOUNT_ALREADY_EXISTS(HttpStatus.CONFLICT,"account already exists"),
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND,"account not found"),
    INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED,"incorrect password"),
    NO_CATEGORY(HttpStatus.BAD_REQUEST,"there's no category"),
    NOTICE_BOARD_NOT_FOUND(HttpStatus.NOT_FOUND,"notice board not found"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"user not found"),
    ALREADY_LIKED(HttpStatus.BAD_REQUEST,"already_liked"),
    NOT_LIKED(HttpStatus.BAD_REQUEST,"not liked"),
    STUDENT_NUM_ALREADY_EXISTS(HttpStatus.CONFLICT,"student number already exists"),
    NO_HEADER(HttpStatus.BAD_REQUEST,"no header"),
    INCORRECT_ORDER_BY(HttpStatus.BAD_REQUEST, "incorrect order by option"),
    OVER_RUN_CATEGORY(HttpStatus.BAD_REQUEST,"over run category"),
    NO_TITLE(HttpStatus.BAD_REQUEST,"no title"),
    DUPLICATE_TITLE(HttpStatus.BAD_REQUEST,"duplicate title"),
    NOT_ACCOUNT_OWNER(HttpStatus.UNAUTHORIZED,"not account owner"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND,"comment not found");

    private final HttpStatus httpStatus;
    private final String message;
}