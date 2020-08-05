package com.example.ewallet.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity handleReourceNotFoundException(
      ResourceNotFoundException exception, HttpServletRequest request) {
    return ResponseEntity.status(NOT_FOUND).body("Resource not found: " + exception.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception, HttpServletRequest request) {
    return ResponseEntity.status(BAD_REQUEST)
        .body("Invalid Data In Body: " + getErrorMessage(exception));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity handleHttpMessageNotReadableException(
      HttpMessageNotReadableException exception, HttpServletRequest request) {
    return ResponseEntity.status(BAD_REQUEST).body("Invalid Json");
  }

  private String getErrorMessage(MethodArgumentNotValidException exception) {
    return exception.getBindingResult().getFieldErrors().stream()
        .map(e -> String.format("[%s] %s", e.getField(), e.getDefaultMessage()))
        .collect(Collectors.joining("; "));
  }
}
