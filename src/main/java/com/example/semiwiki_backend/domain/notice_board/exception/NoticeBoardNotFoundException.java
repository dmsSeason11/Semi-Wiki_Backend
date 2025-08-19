package com.example.semiwiki_backend.domain.notice_board.exception;

public class NoticeBoardNotFoundException extends RuntimeException {
    public NoticeBoardNotFoundException(String message) {
        super(message);
    }
}
