package com.example.library_system.exceptions;

import com.example.library_system.exceptions.custom.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                "User not found",
                HttpStatus.NOT_FOUND.value(),
                List.of(ex.getMessage())
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
