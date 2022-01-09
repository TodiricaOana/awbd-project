package com.example.javaproject.exception;

import com.example.javaproject.exception.definition.*;
import com.example.javaproject.util.Util;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FailedToParseTheBodyException.class)
    public ResponseEntity<Object> handleFailedToParseTheBodyException(FailedToParseTheBodyException ex) {
        return new ResponseEntity<>(Util.generateErrorBody(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<Object> handleEmailAlreadyUsedException(EmailAlreadyUsedException ex) {
        return new ResponseEntity<>(Util.generateErrorBody(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(Util.generateErrorBody(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotMatchingPassword.class)
    public ResponseEntity<Object> handleNotMatchingPasswordException(NotMatchingPassword ex) {
        return new ResponseEntity<>(Util.generateErrorBody(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<Object> handleInternalServerErrorException(InternalServerErrorException ex) {
        return new ResponseEntity<>(Util.generateErrorBody(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(OrderNotFound.class)
    public ResponseEntity<Object> handleCategoryNotFoundException(OrderNotFound ex) {
        return new ResponseEntity<>(Util.generateErrorBody(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFound.class)
    public ResponseEntity<Object> handlePostNotFoundException(ProductNotFound ex) {
        return new ResponseEntity<>(Util.generateErrorBody(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartNotFound.class)
    public ResponseEntity<Object> handleCommentNotFoundException(CartNotFound ex) {
        return new ResponseEntity<>(Util.generateErrorBody(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OutOfStock.class)
    public ResponseEntity<Object> handleMessageNotFoundException(OutOfStock ex) {
        return new ResponseEntity<>(Util.generateErrorBody(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewNotFound.class)
    public ResponseEntity<Object> handleMessageNotFoundException(ReviewNotFound ex) {
        return new ResponseEntity<>(Util.generateErrorBody(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
