package com.mindtree.exceptions;

public class InvalidDateRangeException extends RuntimeException{
    private String message;

    public InvalidDateRangeException(String message) {
        super(message);
        this.message=message;
    }
    public InvalidDateRangeException(){}
}
