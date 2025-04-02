package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleMovieNotFound(MovieNotFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails("movie not found", List.of(ex.getMessage()));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorDetails> handleInvalidRequestException(InvalidRequestException ex){
        ErrorDetails errorDetails = new ErrorDetails("bad request", List.of(ex.getMessage()));
        return  new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
            List<String> messages = ex.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
        ErrorDetails errorDetails = new ErrorDetails("bad request", messages);

        return  new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

    }

}
