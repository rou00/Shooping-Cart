package com.rou00.shopcart.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.print.attribute.standard.JobKOctets;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedExeption(AccessDeniedException ex){
        String message = "You Don't Have Permission to this Action";
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }
}
