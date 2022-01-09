package com.example.javaproject.exception.definition;

public class InternalServerErrorException extends Exception{
    public InternalServerErrorException() {
        super();
    }

    public InternalServerErrorException(String message) {
        super(message);
    }
}
