package com.example.javaproject.mapper;

import com.example.javaproject.dto.CartDto;
import com.example.javaproject.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface CartMapper {

    @Mapping(target = "userId", source = "user.id")
    CartDto mapToDto(Cart cart);

    @Mapping(target = "user.id", source = "userId")
    Cart mapToEntity(CartDto cartDto);
}
