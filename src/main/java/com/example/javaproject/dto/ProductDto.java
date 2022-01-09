package com.example.javaproject.dto;

import com.example.javaproject.model.ProductType;
import com.example.javaproject.model.UserType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class ProductDto {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Price is mandatory")
    private Double price;

    @NotNull(message = "Number of products is mandatory")
    private Integer numberOfProducts;

    private ProductType productType;
}
