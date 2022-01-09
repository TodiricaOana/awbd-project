package com.example.javaproject.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CartDto {

    private Long id;

    private Long userId;

    private List<ProductDto> products;

}
