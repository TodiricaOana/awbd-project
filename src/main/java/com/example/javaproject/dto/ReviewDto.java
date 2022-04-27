package com.example.javaproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private Long id;

    @NotBlank(message = "Review text is mandatory")
    private String text;

    @NotNull(message = "UserId is mandatory")
    private Long userId;

    @NotNull(message = "ProductId is mandatory")
    private Long productId;

}
