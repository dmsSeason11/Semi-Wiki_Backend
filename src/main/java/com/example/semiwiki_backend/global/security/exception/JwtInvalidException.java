package com.example.semiwiki_backend.global.security.exception;

import com.example.semiwiki_backend.global.exception.ErrorCode;
import com.example.semiwiki_backend.global.exception.SemiWikiException;
import lombok.Getter;


@Getter
public class JwtInvalidException extends SemiWikiException {

    public JwtInvalidException(){
        super(ErrorCode.INVALID_TOKEN);
    }


}
