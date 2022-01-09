package com.example.javaproject.controller;

import com.example.javaproject.dto.CartDto;
import com.example.javaproject.dto.OrderDto;
import com.example.javaproject.dto.ProductDto;
import com.example.javaproject.exception.definition.CartNotFound;
import com.example.javaproject.exception.definition.OrderNotFound;
import com.example.javaproject.exception.definition.OutOfStock;
import com.example.javaproject.exception.definition.ProductNotFound;
import com.example.javaproject.model.Order;
import com.example.javaproject.service.CartService;
import com.example.javaproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<OrderDto> placeOrder(@PathVariable Long userId) throws CartNotFound, ProductNotFound, OutOfStock {
        return ResponseEntity
                .ok()
                .body(orderService.placeOrder(userId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersByUser(@PathVariable Long userId) throws OrderNotFound {
        return ResponseEntity
                .ok()
                .body(orderService.findOrdersByUser(userId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) throws OrderNotFound {
        return ResponseEntity
                .ok()
                .body(orderService.findOrderById(orderId));
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<CartDto> deleteOrder(@PathVariable Long orderId) throws OrderNotFound {
        orderService.deleteOrderById(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> get() {
        return ResponseEntity
                .ok()
                .body(orderService.getAllOrders());
    }

    @PutMapping("/update/{id}/{status}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @PathVariable String status) throws OrderNotFound {
        return ResponseEntity
                .ok()
                .body(orderService.updateOrder(status, id));
    }
}
