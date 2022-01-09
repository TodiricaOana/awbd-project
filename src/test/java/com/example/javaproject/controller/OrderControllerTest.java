package com.example.javaproject.controller;

import com.example.javaproject.dto.OrderDto;
import com.example.javaproject.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {
    private static final Long ID = 1L;
    private static final Long USER_ID = 1L;
    private static final String STATUS = "SENT";

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Testing place order")
    void test_create() throws Exception {
        OrderDto dto = getOrderDto();
        Long userId = 1L;
        when(orderService.placeOrder(any())).thenReturn(dto);

        mockMvc.perform(post("/api/order/add/" + userId))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("Testing delete an order")
    void test_deleteOrder() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/order/delete/" + id))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    @DisplayName("Testing get orders by user")
    void test_getOrdersByUser() throws Exception {
        List<OrderDto> dto = List.of(getOrderDto());
        Long userId = 1L;
        when(orderService.findOrdersByUser(any())).thenReturn(dto);

        mockMvc.perform(get("/api/order/user/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Testing get order")
    void test_get() throws Exception {
        OrderDto dto = getOrderDto();
        Long id = 1L;
        when(orderService.findOrderById(any())).thenReturn(dto);

        mockMvc.perform(get("/api/order/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Testing get all orders")
    void test_getAll() throws Exception {
        List<OrderDto> dto = List.of(getOrderDto());
        when(orderService.getAllOrders()).thenReturn(dto);

        mockMvc.perform(get("/api/order/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Testing update status of an order")
    void test_update() throws Exception {
        OrderDto dto = getOrderDto();
        Long id = 1L;
        String status = "SENT";
        when(orderService.updateOrder(any(), any())).thenReturn(dto);

        mockMvc.perform(put("/api/order/update/" + id + "/" + status))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(status)))
                .andReturn();

    }


    private OrderDto getOrderDto() {
        return OrderDto.builder()
                .status(STATUS)
                .id(ID)
                .userId(USER_ID)
                .build();
    }
}