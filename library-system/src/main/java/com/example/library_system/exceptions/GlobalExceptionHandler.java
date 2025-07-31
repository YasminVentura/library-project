package com.example.library_system.exceptions;

import com.example.library_system.exceptions.custom.BookNotFoundException;
import com.example.library_system.exceptions.custom.EmailAlreadyExistsException;
import com.example.library_system.exceptions.custom.IsbnAlreadyExistsException;
import com.example.library_system.exceptions.custom.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


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

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                "Email already exists",
                HttpStatus.CONFLICT.value(),
                List.of(ex.getMessage())
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookValidationException(BookNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                "Book not found",
                HttpStatus.NOT_FOUND.value(),
                List.of(ex.getMessage())
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IsbnAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleIsbnAlreadyExistsException(IsbnAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                "Isbn in use",
                HttpStatus.CONFLICT.value(),
                List.of(ex.getMessage())
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> validationErros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format("Field '%s' %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                "Validation failed",
                HttpStatus.BAD_REQUEST.value(),
                validationErros
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
