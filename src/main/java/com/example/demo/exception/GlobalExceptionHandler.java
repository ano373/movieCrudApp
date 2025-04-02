package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleMovieNotFound(MovieNotFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails("movie not found", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorDetails> handleInvalidRequestException(InvalidRequestException ex){
        ErrorDetails errorDetails = new ErrorDetails("bad request", ex.getMessage());
        return  new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
