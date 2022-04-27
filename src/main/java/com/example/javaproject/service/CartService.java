package com.example.javaproject.service;

import com.example.javaproject.dto.CartDto;
import com.example.javaproject.exception.definition.CartNotFound;
import com.example.javaproject.exception.definition.ProductNotFound;
import com.example.javaproject.mapper.CartMapper;
import com.example.javaproject.model.Cart;
import com.example.javaproject.model.Product;
import com.example.javaproject.model.security.User;
import com.example.javaproject.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class CartService {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    public CartDto findCartByUser(Long userId) throws CartNotFound {
        Cart cart =  cartRepository.findCartByUserId(userId);
        if (cart == null)
            throw new CartNotFound("Cart not found");
        return cartMapper.mapToDto(cart);
    }

    public Cart findById(Long id) throws CartNotFound {
        Optional<Cart> cart =  cartRepository.findById(id);
        if (cart.isEmpty())
            throw new CartNotFound("Cart not found");
        return cart.get();
    }

    public void deleteCartByUserId(Long userId) throws CartNotFound {
        User user = userService.findById(userId);
        Cart cart = findById(user.getCart().getId());

        user.setCart(null);
        cartRepository.delete(cart);
    }

    public CartDto addToCart(Long userId, Long productId) throws ProductNotFound {
        User user = userService.findById(userId);
        Product product = productService.findById(productId);

        Cart cart = user.getCart();

        if (cart == null) {
            cart = new Cart();
        }

        cart.setUser(user);
        user.setCart(cart);
        cart.getProducts().add(product);
        Cart savedCart = cartRepository.save(cart);

        userService.updateUser(user, userId);
        return cartMapper.mapToDto(savedCart);
    }

    public CartDto deleteProductFromCart(Long cartId, Long productId) throws ProductNotFound, CartNotFound {
        Product product = productService.findById(productId);
        Cart cart = findById(cartId);
        cart.getProducts().remove(product);
        Cart savedCart = cartRepository.save(cart);

        return cartMapper.mapToDto(savedCart);
    }

}
