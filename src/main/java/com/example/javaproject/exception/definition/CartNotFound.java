package com.example.javaproject.exception.definition;

public class CartNotFound extends Exception {
    public CartNotFound() {
        super();
    }
    public CartNotFound(String message) {
        super(message);
    }
}
