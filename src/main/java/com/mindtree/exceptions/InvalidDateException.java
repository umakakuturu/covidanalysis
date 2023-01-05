package com.mindtree.exceptions;

public class InvalidDateException extends RuntimeException {
    private String message;

    public InvalidDateException(String message) {
        super(message);
        this.message=message;
    }
    public InvalidDateException(){}
}
