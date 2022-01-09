package com.example.javaproject.exception.definition;

public class OutOfStock extends Exception{
    public OutOfStock() {
        super();
    }

    public OutOfStock(String message) {
        super(message);
    }
}
