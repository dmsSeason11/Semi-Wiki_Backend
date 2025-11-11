package com.example.semiwiki_backend.domain.comment.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;
import com.example.semiwiki_backend.global.exception.SemiWikiException;

public class CommentNotValidException extends SemiWikiException {
  public CommentNotValidException() {
    super(ErrorCode.COMMENT_NOT_VALID);
  }
}
