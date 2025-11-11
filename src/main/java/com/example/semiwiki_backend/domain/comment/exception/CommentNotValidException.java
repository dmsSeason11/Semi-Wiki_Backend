package com.example.semiwiki_backend.domain.comment.exception;

public class CommentNotValidException extends RuntimeException {
  public CommentNotValidException(String message) {
    super(message);
  }
}
