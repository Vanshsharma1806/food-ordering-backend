package com.vansh.foodOrdering.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle RuntimeExceptions (like your duplicate email, user not found)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)   // 400 Bad Request
                .body(ex.getMessage());            // send error message as response body
    }

    // You can add more handlers here, e.g., for validation errors, unauthorized, etc.
}
