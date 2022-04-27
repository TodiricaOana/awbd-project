package com.example.javaproject.controller;

import com.example.javaproject.dto.OrderDto;
import com.example.javaproject.dto.UserDto;
import com.example.javaproject.exception.definition.CartNotFound;
import com.example.javaproject.exception.definition.OrderNotFound;
import com.example.javaproject.exception.definition.OutOfStock;
import com.example.javaproject.exception.definition.ProductNotFound;
import com.example.javaproject.service.OrderService;
import com.example.javaproject.service.UserService;
import com.example.javaproject.service.security.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping
    public String placeOrder() throws CartNotFound, ProductNotFound, OutOfStock {
        String username = jpaUserDetailsService.getCurrentUser().getUsername();
        UserDto user =  userService.findUserByEmail(username);
        orderService.placeOrder(user.getId());
        return "orderSuccess";
    }

    @GetMapping("/{id}")
    public String getOrderById(@PathVariable("id") Long id, Model model) throws OrderNotFound {
        model.addAttribute("order", orderService.findOrderById(id));
        return "orderInfo";
    }

    @DeleteMapping("/delete/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) throws OrderNotFound {
        orderService.deleteOrderById(orderId);
        return "redirect:/orders/all";
    }

    @GetMapping("/all")
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders";
    }

    @GetMapping("/form/{id}")
    public String getOrderByIdForm(@PathVariable("id") Long id, Model model) throws OrderNotFound {
        model.addAttribute("order", orderService.findOrderById(id));
        return "updateOrderForm";
    }

    @PutMapping("/{id}")
    public String updateOrder(@PathVariable Long id, @Valid @ModelAttribute("order") OrderDto order, BindingResult bindingResult ) throws OrderNotFound {
        if (bindingResult.hasErrors()) {
            return "updateOrderForm";
        }
        OrderDto orderDto = orderService.updateOrder(order, id);

        return "redirect:/orders/" + orderDto.getId();
    }
}
