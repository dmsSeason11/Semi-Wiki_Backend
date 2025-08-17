package com.example.semiwiki_backend.domain.like.exception;

public class AlreadyLikedException extends RuntimeException {
    public AlreadyLikedException(String message) {
        super(message);
    }
}
