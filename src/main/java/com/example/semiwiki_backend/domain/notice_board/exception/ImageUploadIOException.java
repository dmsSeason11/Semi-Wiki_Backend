package com.example.semiwiki_backend.domain.notice_board.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;
import com.example.semiwiki_backend.global.exception.SemiWikiException;

public class ImageUploadIOException extends SemiWikiException {
  public ImageUploadIOException() {
    super(ErrorCode.IMAGE_UPLOAD_ERROR);
  }
}
