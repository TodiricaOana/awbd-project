package com.example.javaproject.exception.definition;

public class ProductNotFound extends Exception {
    public ProductNotFound() { super(); }
    public ProductNotFound(String message) {
        super(message);
    }
}
