package com.quorum.quorumapi.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(EmailAlreadyUsedError.class)
  public ResponseEntity<ErrorInfo> userAlreadyExists(EmailAlreadyUsedError err) {
    final String errorMessage = "Email already used";
    var errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), errorMessage);
    return ResponseEntity.badRequest().body(errorInfo);
  }

  @ExceptionHandler(UsernameNotFoundError.class)
  public ResponseEntity<ErrorInfo> userNameNotFound(UsernameNotFoundError err) {
    final String errorMessage = "There is no user registered with that email";
    var errorInfo = new ErrorInfo(HttpStatus.NOT_FOUND.value(), errorMessage);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorInfo);
  }

  @ExceptionHandler(PostDuplicationError.class)
  public ResponseEntity<ErrorInfo> postDuplicationError(PostDuplicationError err) {
    final String errorMessage = "Duplicated post";
    var errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), errorMessage);
    return ResponseEntity.badRequest().body(errorInfo);
  }

  @ExceptionHandler(PostNotFoundError.class)
  public ResponseEntity<ErrorInfo> postNotFoundError(PostNotFoundError err) {
    final String errorMessage = "There is no post with id " + err.getId();
    var errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), errorMessage);
    return ResponseEntity.badRequest().body(errorInfo);
  }

  @ExceptionHandler(UserNotFoundError.class)
  public ResponseEntity<ErrorInfo> userNotFoundError(UserNotFoundError err) {
    final String errorMessage = "User with id " + err.getId() + "was not found";
    var errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), errorMessage);
    return ResponseEntity.badRequest().body(errorInfo);
  }

  @ExceptionHandler(InvalidCredentials.class)
  public ResponseEntity<ErrorInfo> invalidCredential(InvalidCredentials err) {
    final String errorMessage = "Current user can't do that, invalid authorization";
    var errorInfo = new ErrorInfo(HttpStatus.UNAUTHORIZED.value(), errorMessage);
    return ResponseEntity.badRequest().body(errorInfo);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorInfo> invalidArgument(MethodArgumentNotValidException err) {
    var errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), err.getBody().getDetail());
    return ResponseEntity.badRequest().body(errorInfo);
  }

  @ExceptionHandler(ValidationError.class)
  public ResponseEntity<ErrorInfo> validationError(ValidationError err) {
    var errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), err.getMessage());
    return ResponseEntity.badRequest().body(errorInfo);
  }
}
