package com.example.javaproject.exception.definition;

public class EmailAlreadyUsedException extends Exception {
    public EmailAlreadyUsedException() {
        super();
    }

    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
