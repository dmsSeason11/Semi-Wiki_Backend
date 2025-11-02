package com.example.semiwiki_backend.global.handler;


import com.example.semiwiki_backend.domain.auth.exception.*;
import com.example.semiwiki_backend.global.exception.SemiWikiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SemiWikiException.class)
    public ResponseEntity<String> handleSemiWikiException(SemiWikiException e){
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(NotAccountOwnerException.class)
    public ResponseEntity<String> handleNotAccountOwnerException(NotAccountOwnerException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

}
