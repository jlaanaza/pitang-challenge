package com.pitang.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<ExceptionError> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        //need to better this responseCode
        ExceptionError exceptionError = new ExceptionError(exception.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(exceptionError, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }
}
