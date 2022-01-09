package com.example.javaproject.service;

import com.example.javaproject.dto.OrderDto;
import com.example.javaproject.dto.ProductDto;
import com.example.javaproject.exception.definition.*;
import com.example.javaproject.mapper.OrderMapper;
import com.example.javaproject.mapper.ProductMapper;
import com.example.javaproject.model.*;
import com.example.javaproject.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    private static final Long USER_ID = 1L;
    private static final Long CART_ID = 1L;
    private static final Long ORDER_ID = 1L;
    private static final String STATUS = "dummyStatus";
    private static final String EMAIL = "dummy@gmail.com";
    private static final String NAME = "dummyName";

    @Mock
    private ProductMapper productMapper;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @Mock
    private CartService cartService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("Create order - works")
    void create_works() throws CartNotFound, ProductNotFound, OutOfStock {
        User user = getUser();
        Cart cart = getCart();
        user.setCart(cart);
        Long prodId = 1L;

        Product product = getProduct();
        product.setNumberOfProducts(1);
        product.setId(prodId);
        List<Product> products = List.of(product);
        cart.setProducts(products);

        OrderDto orderDto = getOrderDto();

        Order savedOrder = getOrder();

        when(userService.findById(USER_ID)).thenReturn(user);
        when(cartService.findById(CART_ID)).thenReturn(cart);
        when(productService.findById(prodId)).thenReturn(product);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);
        when(orderMapper.mapToDto(savedOrder)).thenReturn(orderDto);

        OrderDto result = orderService.placeOrder(USER_ID);
        assertNotNull(result);
        assertEquals(orderDto.getId(), result.getId());
    }

    @Test
    @DisplayName("delete- works")
    void delete_works() throws OrderNotFound {
        Order order = getOrder();
        order.setId(ORDER_ID);
        Optional<Order> optionalOrder = Optional.of(order);

        when(orderRepository.findById(ORDER_ID)).thenReturn(optionalOrder);

        orderService.deleteOrderById(ORDER_ID);

        verify(orderRepository, times(1)).delete(order);
    }

    @Test
    @DisplayName("delete- fails")
    void delete_fails()  {
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.empty());

        assertThrows(OrderNotFound.class, () -> orderService.deleteOrderById(ORDER_ID));
    }

    @Test
    @DisplayName("Get all orders - works")
    void getAll_works() {
        List<Order> orders = List.of(getOrder());
        List<OrderDto> orderDtos =  List.of(getOrderDto());

        when(orderRepository.findAll()).thenReturn(orders);
        when(orderMapper.mapToDto(orders.get(0))).thenReturn(orderDtos.get(0));

        List<OrderDto> result = orderService.getAllOrders();

        assertNotNull(result);
        assertEquals(orderDtos.get(0).getId(), result.get(0).getId());
    }

    @Test
    @DisplayName("Update order - works")
    void update_works() throws ProductNotFound, OrderNotFound {
        Order order = getOrder();

        Order orderFind = getOrder();
        Optional<Order> optionalOrder = Optional.of(orderFind);

        Order savedOrder = getOrder();
        savedOrder.setId(ORDER_ID);

        OrderDto returnedOrderDTO = getOrderDto();
        returnedOrderDTO.setId(ORDER_ID);

        when(orderRepository.findById(ORDER_ID)).thenReturn(optionalOrder);
        when(orderRepository.save(order)).thenReturn(savedOrder);
        when(orderMapper.mapToDto(savedOrder)).thenReturn(returnedOrderDTO);

        OrderDto result = orderService.updateOrder(STATUS, ORDER_ID);

        assertNotNull(result);
        assertEquals(returnedOrderDTO.getId(), result.getId());
        assertEquals(returnedOrderDTO.getStatus(), result.getStatus());
    }

    @Test
    @DisplayName("find order by id - works")
    void findById_works() throws OrderNotFound {
        Order order = getOrder();
        order.setId(ORDER_ID);
        Optional<Order> optionalOrder = Optional.of(order);
        OrderDto orderDto = getOrderDto();

        when(orderRepository.findById(ORDER_ID)).thenReturn(optionalOrder);
        when(orderMapper.mapToDto(optionalOrder.get())).thenReturn(orderDto);

        OrderDto result = orderService.findOrderById(ORDER_ID);

        assertNotNull(result);
        assertEquals(orderDto.getId(), result.getId());
        assertEquals(orderDto.getStatus(), result.getStatus());
    }

    private Product getProduct() {
        Product product = new Product();
        product.setName(NAME);

        return product;
    }

    private ProductDto getProductDto() {
        return ProductDto.builder()
                .name(NAME)
                .build();
    }

    private User getUser() {
        User user = new User();
        user.setEmail(EMAIL);

        return user;
    }

    private OrderDto getOrderDto() {
        return OrderDto.builder()
                .status(STATUS)
                .build();
    }

    private Order getOrder() {
        Order order = new Order();
        order.setStatus(STATUS);
        User user = getUser();
        Product product = getProduct();
        order.setUser(user);
        order.setProducts(List.of(product));

        return order;
    }

    private Cart getCart() {
        Cart cart = new Cart();
        cart.setId(CART_ID);
        User user = getUser();
        Product product = getProduct();
        cart.setUser(user);
        cart.setProducts(List.of(product));

        return cart;
    }
}