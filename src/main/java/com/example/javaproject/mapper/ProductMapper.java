package com.example.javaproject.mapper;

import com.example.javaproject.dto.ProductDto;
import com.example.javaproject.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto mapToDto(Product product);

    Product mapToEntity(ProductDto productDTO);
}
