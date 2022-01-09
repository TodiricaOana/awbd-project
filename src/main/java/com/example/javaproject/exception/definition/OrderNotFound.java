package com.example.javaproject.exception.definition;

public class OrderNotFound extends Exception {
    public OrderNotFound() {
        super();
    }
    public OrderNotFound(String message) {
        super(message);
    }
}
