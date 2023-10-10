package com.pitang.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<ExceptionError> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        ExceptionError exceptionError = new ExceptionError(exception.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(exceptionError, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ExceptionError> handleNotFoundException(NotFoundException exception) {
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ExceptionError> handleDataIntegrityViolationException(DataIntegrityViolationException exception){
        String errorMessage = exception.getMessage() != null ? exception.getMessage() :"";

        String responseMessage = "";

        if (errorMessage.contains( "USER_UNIQUE_EMAIL" )) {
            responseMessage += "Email already exists";
        } else if (errorMessage.contains( "USER_UNIQUE_LOGIN" )) {
            responseMessage += "Login already exists";
        } else if (errorMessage.contains( "CAR_UNIQUE_LICENSE_PLATE" )) {
            responseMessage += "License plate already exists";

        } else {
            responseMessage += "Data Integrity Error";
        }
        ExceptionError exceptionError = new ExceptionError(responseMessage, HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(exceptionError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        ExceptionError exceptionError = new ExceptionError(errorMessage, HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(exceptionError, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionError> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ExceptionError exceptionError = new ExceptionError("Invalid fields", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionError, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }


}
