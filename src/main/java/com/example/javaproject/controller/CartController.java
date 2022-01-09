package com.example.javaproject.controller;

import com.example.javaproject.dto.CartDto;
import com.example.javaproject.exception.definition.CartNotFound;
import com.example.javaproject.exception.definition.ProductNotFound;
import com.example.javaproject.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add/{userId}/{productId}")
    public ResponseEntity<CartDto> addToCart(@PathVariable Long userId, @PathVariable Long productId) throws ProductNotFound, NoSuchAlgorithmException {
        return ResponseEntity
                .ok()
                .body(cartService.addToCart(userId, productId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCartByUser(@PathVariable Long userId) throws CartNotFound {
        return ResponseEntity
                .ok()
                .body(cartService.findCartByUser(userId));
    }

    @PutMapping("/delete/{cartId}/{productId}")
    public ResponseEntity<CartDto> deleteProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) throws ProductNotFound, CartNotFound {
        return ResponseEntity
                .ok()
                .body(cartService.deleteProductFromCart(cartId, productId));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<CartDto> deleteCartByUserId(@PathVariable Long userId) throws CartNotFound {
        cartService.deleteCartByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<CartDto>> get() {
        return ResponseEntity
                .ok()
                .body(cartService.getAllCarts());
    }
}
