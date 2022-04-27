package com.example.javaproject.exception;

import com.example.javaproject.exception.definition.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import lombok.extern.slf4j.Slf4j;


@ControllerAdvice
@Slf4j
public class ControllerAdvisor {

    @ExceptionHandler(CartNotFound.class)
    public ModelAndView handleCartNotFoundException(CartNotFound exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("exception", exception);
        modelAndView.setViewName("customError");

        log.info(exception.getMessage(), exception);
        return modelAndView;
    }

    @ExceptionHandler(FailedToParseTheBodyException.class)
    public ModelAndView handleFailedToParseTheBodyException(FailedToParseTheBodyException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("exception", exception);
        modelAndView.setViewName("customError");

        log.info(exception.getMessage(), exception);
        return modelAndView;
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ModelAndView handleEmailAlreadyUsedException(EmailAlreadyUsedException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("exception", exception);
        modelAndView.setViewName("customError");

        log.info(exception.getMessage(), exception);
        return modelAndView;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView handleUserNotFoundException(UserNotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("exception", exception);
        modelAndView.setViewName("customError");

        log.info(exception.getMessage(), exception);
        return modelAndView;
    }

    @ExceptionHandler(NotMatchingPassword.class)
    public ModelAndView handleNotMatchingPasswordException(NotMatchingPassword exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("exception", exception);
        modelAndView.setViewName("customError");

        log.info(exception.getMessage(), exception);
        return modelAndView;
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ModelAndView handleInternalServerErrorException(InternalServerErrorException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("exception", exception);
        modelAndView.setViewName("customError");

        log.info(exception.getMessage(), exception);
        return modelAndView;
    }

    @ExceptionHandler(OrderNotFound.class)
    public ModelAndView handleOrderNotFoundException(OrderNotFound exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("exception", exception);
        modelAndView.setViewName("customError");

        log.info(exception.getMessage(), exception);
        return modelAndView;
    }

    @ExceptionHandler(ProductNotFound.class)
    public ModelAndView handleProductNotFoundException(ProductNotFound exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("exception", exception);
        modelAndView.setViewName("customError");

        log.info(exception.getMessage(), exception);
        return modelAndView;
    }

    @ExceptionHandler(OutOfStock.class)
    public ModelAndView handleOutOfStockException(OutOfStock exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("exception", exception);
        modelAndView.setViewName("customError");

        log.info(exception.getMessage(), exception);
        return modelAndView;
    }

    @ExceptionHandler(ReviewNotFound.class)
    public ModelAndView handleReviewNotFoundException(ReviewNotFound exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("exception", exception);
        modelAndView.setViewName("customError");

        log.info(exception.getMessage(), exception);
        return modelAndView;
    }
}
