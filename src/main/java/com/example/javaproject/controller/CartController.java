package com.example.javaproject.controller;

import com.example.javaproject.dto.CartDto;
import com.example.javaproject.dto.UserDto;
import com.example.javaproject.exception.definition.CartNotFound;
import com.example.javaproject.exception.definition.ProductNotFound;
import com.example.javaproject.model.security.User;
import com.example.javaproject.service.CartService;
import com.example.javaproject.service.UserService;
import com.example.javaproject.service.security.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping("/{productId}")
    public String addToCart(@PathVariable Long productId) throws ProductNotFound {

        String username = jpaUserDetailsService.getCurrentUser().getUsername();
        UserDto user =  userService.findUserByEmail(username);
        cartService.addToCart(user.getId(), productId);

        return "redirect:/cart";
    }

    @GetMapping
    public String getCurrentCartByUser(Model model) throws CartNotFound {
        String username = jpaUserDetailsService.getCurrentUser().getUsername();
        UserDto user =  userService.findUserByEmail(username);
        model.addAttribute("cart", cartService.findCartByUser(user.getId()));
        return ("cart");
    }

    @PutMapping("/{cartId}/{productId}")
    public String deleteProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) throws ProductNotFound, CartNotFound {
       cartService.deleteProductFromCart(cartId, productId);
       return "redirect:/cart";
    }
}
