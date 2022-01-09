package com.example.javaproject.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class ReviewDto {

    private Long id;

    @NotBlank(message = "Review text is mandatory")
    private String text;

    @NotNull(message = "UserId is mandatory")
    private Long userId;

    @NotNull(message = "ProductId is mandatory")
    private Long productId;

}
