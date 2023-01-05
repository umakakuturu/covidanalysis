package com.mindtree.exceptions;

public class NoDataFoundException extends RuntimeException {
    private String message;

    public NoDataFoundException(String message) {
        super(message);
        this.message=message;
    }
    public NoDataFoundException(){}

}
