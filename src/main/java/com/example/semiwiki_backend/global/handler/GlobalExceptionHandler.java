package com.example.semiwiki_backend.global.handler;

import com.example.semiwiki_backend.domain.like.exception.AlreadyLikedException;
import com.example.semiwiki_backend.domain.like.exception.NotLikedException;
import com.example.semiwiki_backend.domain.notice_board.exception.NoCategoryException;
import com.example.semiwiki_backend.domain.notice_board.exception.NoticeBoardNotFoundException;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(NoCategoryException.class)
    public ResponseEntity<String> handleNoCategoryException(NoCategoryException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(NoticeBoardNotFoundException.class)
    public ResponseEntity<String> handleNoticeBoardNotFoundException(NoticeBoardNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(NotLikedException.class)
    public ResponseEntity<String> handleNotLikedException(NotLikedException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(AlreadyLikedException.class)
    public ResponseEntity<String> handleAlreadyLikedException(AlreadyLikedException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
