package com.example.demo.controllers;

import org.apache.log4j.Logger;

public class ApiException extends RuntimeException {

    private ExceptionTypes exceptionType;
    private static final Logger log = Logger.getLogger(ApiException.class);

    public ApiException(){   }
    public ApiException(ExceptionTypes exceptionType, String message){
        super(message);
        this.exceptionType = exceptionType;
        log.info("ApiException = " + this.getExceptionType() + " user = " + message);
    }

    public ExceptionTypes getExceptionType() {
        return exceptionType;
    }
}
