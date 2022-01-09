package com.example.javaproject.service;

import com.example.javaproject.dto.CartDto;
import com.example.javaproject.exception.definition.CartNotFound;
import com.example.javaproject.exception.definition.ProductNotFound;
import com.example.javaproject.mapper.CartMapper;
import com.example.javaproject.model.Cart;
import com.example.javaproject.model.Product;
import com.example.javaproject.model.User;
import com.example.javaproject.model.UserType;
import com.example.javaproject.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Transactional
    public void deleteCartByUserId(Long userId) throws CartNotFound {
        User user = userService.findById(userId);
        Cart cart = findById(user.getCart().getId());

        user.setCart(null);
        cartRepository.delete(cart);
    }

    public CartDto addToCart(Long userId, Long productId) throws ProductNotFound, NoSuchAlgorithmException {
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

        userService.updateUser(user, userId, UserType.USER);
        return cartMapper.mapToDto(savedCart);
    }

    public List<CartDto> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        List <CartDto> cartsDto = carts.stream().map(product -> cartMapper.mapToDto(product))
                .collect(Collectors.toList());
        return cartsDto;
    }

    public CartDto deleteProductFromCart(Long cartId, Long productId) throws ProductNotFound, CartNotFound {
        Product product = productService.findById(productId);
        Cart cart = findById(cartId);
        cart.getProducts().remove(product);
        Cart savedCart = cartRepository.save(cart);

        return cartMapper.mapToDto(savedCart);
    }

}
