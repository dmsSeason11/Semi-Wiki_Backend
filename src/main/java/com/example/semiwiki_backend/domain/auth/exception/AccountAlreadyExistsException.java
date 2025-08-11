package com.example.semiwiki_backend.domain.auth.exception;

public class AccountAlreadyExistsException extends RuntimeException {
  public AccountAlreadyExistsException(String message) {
    super(message);
  }
}
