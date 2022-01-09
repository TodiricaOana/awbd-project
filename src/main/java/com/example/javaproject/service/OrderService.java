package com.example.javaproject.service;

import com.example.javaproject.dto.CartDto;
import com.example.javaproject.dto.OrderDto;
import com.example.javaproject.dto.ProductDto;
import com.example.javaproject.exception.definition.CartNotFound;
import com.example.javaproject.exception.definition.OrderNotFound;
import com.example.javaproject.exception.definition.OutOfStock;
import com.example.javaproject.exception.definition.ProductNotFound;
import com.example.javaproject.mapper.CartMapper;
import com.example.javaproject.mapper.OrderMapper;
import com.example.javaproject.mapper.ProductMapper;
import com.example.javaproject.mapper.UserMapper;
import com.example.javaproject.model.*;
import com.example.javaproject.repository.CartRepository;
import com.example.javaproject.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;


    public List<OrderDto> findOrdersByUser(Long userId) throws OrderNotFound {
        List<Order> orders =  orderRepository.findOrdersByUserId(userId);
        if (orders == null)
            throw new OrderNotFound("Orders not found");
        List <OrderDto> ordersDto = orders.stream().map(order -> orderMapper.mapToDto(order))
                .collect(Collectors.toList());
        return ordersDto;
    }

    public Order findById(Long id) throws OrderNotFound {
        Optional<Order> order =  orderRepository.findById(id);
        if (order.isEmpty())
            throw new OrderNotFound("Order not found");
        return order.get();
    }

    public OrderDto findOrderById(Long id) throws OrderNotFound {
        Optional<Order> order =  orderRepository.findById(id);
        if (order.isEmpty())
            throw new OrderNotFound("Order not found");
        return orderMapper.mapToDto(order.get());
    }

    @Transactional
    public void deleteOrderById(Long orderId) throws OrderNotFound {
        Order order = findById(orderId);
        orderRepository.delete(order);
    }

    @Transactional
    public OrderDto placeOrder(Long userId) throws CartNotFound, ProductNotFound, OutOfStock {
        User user = userService.findById(userId);
        Cart cart = cartService.findById(user.getCart().getId());
        Order order = createOrder(user, cart);
        List <Product> products = cart.getProducts();

        for(Product product : products) {
            Product prod = productService.findById(product.getId());
            Integer nrOfProducts = prod.getNumberOfProducts();
            if(nrOfProducts == 0) {
                throw new OutOfStock("Out of stock");
            }
            product.setNumberOfProducts(nrOfProducts - 1);
            productService.updateProduct(product, product.getId());
        }

        cartService.deleteCartByUserId(userId);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.mapToDto(savedOrder);
    }

    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List <OrderDto> ordersDto = orders.stream().map(order -> orderMapper.mapToDto(order))
                .collect(Collectors.toList());
        return ordersDto;
    }

    public OrderDto updateOrder(String status, Long id) throws OrderNotFound {
        Order order = findById(id);
        order.setStatus(status);
        return orderMapper.mapToDto(orderRepository.save(order));
    }

    private Order createOrder(User user, Cart cart) {
        Order order = new Order();

        order.setUser(user);
        order.setProducts(cart.getProducts());
        order.setStatus("SENT");

        return order;
    }
}
