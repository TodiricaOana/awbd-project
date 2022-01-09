package com.example.javaproject.exception.definition;

public class NotMatchingPassword extends Exception {
    public NotMatchingPassword() {
        super();
    }

    public NotMatchingPassword(String message) {
        super(message);
    }
}
