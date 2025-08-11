package com.example.semiwiki_backend.domain.notice_table.exception;

public class NoticeTableNotFoundException extends RuntimeException {
    public NoticeTableNotFoundException(String message) {
        super(message);
    }
}
