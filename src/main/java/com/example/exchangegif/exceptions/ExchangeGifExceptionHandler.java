package com.example.exchangegif.exceptions;

import com.example.exchangegif.responses.DefaultErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExchangeGifExceptionHandler {

    @ExceptionHandler(DefaultException.class)
    public ResponseEntity<Object> handleDefaultException(DefaultException e) {
        DefaultErrorResponse defaultErrorResponse = new DefaultErrorResponse(e.getMessage());
        return new ResponseEntity<>(defaultErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadParametersException.class)
    public ResponseEntity<Object> handleBadParametersException(BadParametersException e) {
        DefaultErrorResponse defaultErrorResponse = new DefaultErrorResponse("bad parameters");
        return new ResponseEntity<>(defaultErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
