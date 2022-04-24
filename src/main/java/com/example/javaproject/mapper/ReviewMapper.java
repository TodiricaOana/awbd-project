package com.example.javaproject.mapper;

import com.example.javaproject.dto.ReviewDto;
import com.example.javaproject.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "productId", source = "product.id")
    ReviewDto mapToDto(Review review);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "product.id", source = "productId")
    Review mapToEntity(ReviewDto reviewDto);
}
