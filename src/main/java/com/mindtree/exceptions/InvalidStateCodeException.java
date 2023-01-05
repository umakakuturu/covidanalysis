package com.mindtree.exceptions;

public class InvalidStateCodeException extends RuntimeException{

    private String message;

    public InvalidStateCodeException(String message) {
        super(message);
        this.message=message;
    }
    public InvalidStateCodeException(){}
}
