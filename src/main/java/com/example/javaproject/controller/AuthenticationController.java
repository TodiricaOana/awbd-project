package com.example.javaproject.controller;

import com.example.javaproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthenticationController {

    @Autowired
    private ProductService productService;

    @GetMapping("/login-form")
    public String showLogInForm() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError() {
        return "loginError";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "accessDenied";
    }

    @RequestMapping({"", "/", "/index"})
    public ModelAndView productsList(){
        ModelAndView modelAndView = new ModelAndView("products");
        modelAndView.addObject("products", productService.getAllProducts());
        return modelAndView;
    }
}
