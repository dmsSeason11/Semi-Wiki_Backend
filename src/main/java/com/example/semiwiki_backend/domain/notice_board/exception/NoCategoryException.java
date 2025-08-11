package com.example.semiwiki_backend.domain.notice_board.exception;

public class NoCategoryException extends RuntimeException {
    public NoCategoryException(String message) {
        super(message);
    }
}
