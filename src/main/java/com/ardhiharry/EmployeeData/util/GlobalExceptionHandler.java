package com.ardhiharry.EmployeeData.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<ApiResponse<String>> illegalStateException(IllegalStateException err) {
    ApiResponse<String> response = ApiResponse.error(
        HttpStatus.BAD_REQUEST.value(),
        err.getMessage(),
        "Bad Request"
    );
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException err) {
    ApiResponse<String> response = ApiResponse.error(
        HttpStatus.UNPROCESSABLE_ENTITY.value(),
        err.getMessage(),
        "Unprocessable Entity"
    );
    return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<String>> handleGeneralException(Exception err) {
    ApiResponse<String> response = ApiResponse.error(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        err.getMessage(),
        "Internal Server Error"
    );
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
