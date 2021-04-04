package com.spring.hrmanagement.exception;

public class DbOperationException extends RuntimeException {

    public DbOperationException() {
        super();
    }

    public DbOperationException(String message) {
        super(message);
    }
}
