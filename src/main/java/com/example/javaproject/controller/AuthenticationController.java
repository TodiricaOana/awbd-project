package com.example.javaproject.controller;

import com.example.javaproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthenticationController {

    @Autowired
    private ProductService productService;

    @GetMapping("/login-form")
    public String showLogInForm() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }

        return "redirect:/index";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken || authentication == null) {
            model.addAttribute("errorMessage", "Invalid username or password!");
            return "loginError";
        }

        return "redirect:/index";
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
