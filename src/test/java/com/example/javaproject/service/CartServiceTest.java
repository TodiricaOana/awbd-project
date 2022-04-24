package com.example.javaproject.service;

import com.example.javaproject.dto.CartDto;
import com.example.javaproject.dto.UserDto;
import com.example.javaproject.exception.definition.CartNotFound;
import com.example.javaproject.exception.definition.ProductNotFound;
import com.example.javaproject.exception.definition.ReviewNotFound;
import com.example.javaproject.mapper.CartMapper;
import com.example.javaproject.model.*;
import com.example.javaproject.model.security.User;
import com.example.javaproject.repository.CartRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    private static final Long ID = 1L;
    private static final Long USER_ID = 1L;
    private static final Long PRODUCT_ID = 1L;
    private static final String EMAIL = "dummy@gmail.com";
    private static final String NAME = "dummyName";

    @Mock
    private CartMapper cartMapper;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CartService cartService;

    @Test
    @DisplayName("Create cart - works")
    void create_works() throws ProductNotFound, NoSuchAlgorithmException {

        User user = getUser();
        user.setId(USER_ID);

        Product product = getProduct();
        product.setId(PRODUCT_ID);

        Cart savedCart = getCart();
        savedCart.setId(ID);

        CartDto returnedCartDto = getCartDto();
        returnedCartDto.setId(ID);

        UserDto userUpdated = getUserDto();

        when(userService.findById(USER_ID)).thenReturn(user);
        when(productService.findById(PRODUCT_ID)).thenReturn(product);
        when(cartRepository.save(any(Cart.class))).thenReturn(savedCart);
        when(cartMapper.mapToDto(savedCart)).thenReturn(returnedCartDto);
        when(userService.updateUser(user, USER_ID)).thenReturn(userUpdated);

        CartDto result = cartService.addToCart(USER_ID, PRODUCT_ID);

        assertNotNull(result);
        assertEquals(returnedCartDto.getId(), result.getId());
        assertEquals(returnedCartDto.getUserId(), result.getUserId());
    }

    @Test
    @DisplayName("Get all - works")
    void getAll_works() throws ReviewNotFound {
        List<Cart> carts = List.of(getCart());
        List<CartDto> cartDtos = List.of(getCartDto());

        when(cartRepository.findAll()).thenReturn(carts);
        when(cartMapper.mapToDto(carts.get(0))).thenReturn(cartDtos.get(0));

        List<CartDto> result = cartService.getAllCarts();

        assertNotNull(result);
        assertEquals(result.get(0).getId(), cartDtos.get(0).getId());
    }

    @Test
    @DisplayName("delete cart by user id- works")
    void deleteByUserId_works() throws CartNotFound {
        Cart cart = getCart();
        cart.setId(ID);

        User user = getUser();
        user.setId(USER_ID);
        user.setCart(cart);
        cart.setUser(user);

        Optional<Cart> optionalCart = Optional.of(cart);

        when(userService.findById(USER_ID)).thenReturn(user);
        when(cartRepository.findById(ID)).thenReturn(optionalCart);

        cartService.deleteCartByUserId(USER_ID);

        verify(cartRepository, times(1)).delete(cart);
    }

    @Test
    @DisplayName("delete product from cart - works")
    void deleteProductFromCart_works() throws CartNotFound, ProductNotFound {
        Cart cart = getCart();
        cart.setId(ID);

        Product product = getProduct();
        product.setId(PRODUCT_ID);

        cart.setProducts(new ArrayList<>(List.of(product)));

        Optional<Cart> optionalCart = Optional.of(cart);

        Cart savedCart = getCart();

        CartDto cartDto = getCartDto();
        cartDto.setProducts(List.of());

        when(productService.findById(PRODUCT_ID)).thenReturn(product);
        when(cartRepository.findById(ID)).thenReturn(optionalCart);
        when(cartRepository.save(cart)).thenReturn(savedCart);
        when(cartMapper.mapToDto(savedCart)).thenReturn(cartDto);

        CartDto result = cartService.deleteProductFromCart(ID, PRODUCT_ID);

        assertNotNull(result);
        assertEquals(cartDto.getId(), result.getId());
        assertEquals(cartDto.getProducts(), result.getProducts());
    }

    private Product getProduct() {
        Product product = new Product();
        product.setName(NAME);

        return product;
    }

    private User getUser() {
        User user = new User();
        user.setEmail(EMAIL);

        return user;
    }

    private CartDto getCartDto() {
        return CartDto.builder()
                .build();
    }

    private Cart getCart() {
        Cart cart = new Cart();
        cart.setId(ID);
        User user = getUser();
        Product product = getProduct();
        cart.setUser(user);
        cart.setProducts(List.of(product));

        return cart;
    }

    private UserDto getUserDto() {
        return UserDto.builder()
                .email(EMAIL)
                .build();
    }

}