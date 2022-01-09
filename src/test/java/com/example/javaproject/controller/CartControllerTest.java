package com.example.javaproject.controller;

import com.example.javaproject.dto.CartDto;
import com.example.javaproject.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CartController.class)
class CartControllerTest {
    private static final Long ID = 1L;
    private static final long USER_ID = 1;

    @MockBean
    private CartService cartService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Testing add to cart")
    void test_create() throws Exception {
        CartDto dto = getCartDto();
        Long userId = 1L;
        Long productId = 1L;
        when(cartService.addToCart(any(), any())).thenReturn(dto);

        mockMvc.perform(post("/api/cart/add/" + userId + "/" + productId))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("Testing delete a product from cart")
    void test_deleteProductFromCart() throws Exception {
        Long id = 1L;
        Long productId = 1L;

        mockMvc.perform(put("/api/cart/delete/" + id + "/" + productId))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("Testing delete cart")
    void test_delete() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/cart/delete/" + id))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    @DisplayName("Testing get cart by user")
    void test_get() throws Exception {
        CartDto dto = getCartDto();
        Long userId = 1L;
        when(cartService.findCartByUser(any())).thenReturn(dto);

        mockMvc.perform(get("/api/cart/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Testing get all products")
    void test_getAll() throws Exception {
        List<CartDto> dto = List.of(getCartDto());
        when(cartService.getAllCarts()).thenReturn(dto);

        mockMvc.perform(get("/api/cart/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    private CartDto getCartDto() {
        return CartDto.builder()
                .id(ID)
                .userId(USER_ID)
                .build();
    }
}