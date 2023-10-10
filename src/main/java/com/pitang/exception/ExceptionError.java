package com.pitang.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionError {
    private String message;
    private int errorCode;
}
