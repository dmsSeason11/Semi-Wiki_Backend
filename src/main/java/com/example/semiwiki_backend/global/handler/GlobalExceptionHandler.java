package com.example.semiwiki_backend.global.handler;


import com.example.semiwiki_backend.domain.auth.exception.AccountAlreadyExistsException;
import com.example.semiwiki_backend.domain.auth.exception.AccountNotFoundException;
import com.example.semiwiki_backend.domain.auth.exception.IncorrectPasswordException;
import com.example.semiwiki_backend.domain.auth.exception.StudentNumAlreadyExistsException;
import com.example.semiwiki_backend.domain.notice_board.exception.*;
import com.example.semiwiki_backend.domain.user_like.exception.AlreadyLikedException;
import com.example.semiwiki_backend.domain.user_like.exception.NotLikedException;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.global.exception.SemiWikiException;
import com.example.semiwiki_backend.global.security.exception.JwtExpiredException;
import com.example.semiwiki_backend.global.security.exception.JwtInvalidException;
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

}
