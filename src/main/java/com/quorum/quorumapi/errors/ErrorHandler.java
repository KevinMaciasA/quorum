package com.quorum.quorumapi.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(EmailAlreadyUsedError.class)
  public ResponseEntity<ErrorInfo> userAlreadyExists(EmailAlreadyUsedError err) {
    final String errorMessage = "Email already used";
    var errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST, errorMessage);
    return ResponseEntity.badRequest().body(errorInfo);
  }

  @ExceptionHandler(UsernameNotFoundError.class)
  public ResponseEntity<ErrorInfo> userNameNotFound(UsernameNotFoundError err) {
    final String errorMessage = "There is no user registered with that email";
    var errorInfo = new ErrorInfo(HttpStatus.NOT_FOUND, errorMessage);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorInfo);
  }

  @ExceptionHandler(PostDuplicationError.class)
  public ResponseEntity<ErrorInfo> postDuplicationError(PostDuplicationError err) {
    final String errorMessage = "Duplicated post";
    var errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST, errorMessage);
    return ResponseEntity.badRequest().body(errorInfo);
  }
}
