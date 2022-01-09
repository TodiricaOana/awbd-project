package com.example.javaproject.exception.definition;

public class FailedToParseTheBodyException extends Exception {
    public FailedToParseTheBodyException() {
        super();
    }

    public FailedToParseTheBodyException(String message) {
        super(message);
    }
}
