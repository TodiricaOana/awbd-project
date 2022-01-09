package com.example.javaproject.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class OrderDto {

    private Long id;

    private String status;

    private Long userId;

    private List<ProductDto> products;

}
