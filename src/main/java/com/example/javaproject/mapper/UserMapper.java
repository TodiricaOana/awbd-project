package com.example.javaproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.javaproject.model.User;
import com.example.javaproject.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "address", source = "userDetails.address")
    @Mapping(target = "phoneNumber", source = "userDetails.phoneNumber")
    @Mapping(target = "otherInformation", source = "userDetails.otherInformation")
    UserDto mapToDto(User user);

    @Mapping(target = "userDetails.address", source = "address")
    @Mapping(target = "userDetails.phoneNumber", source = "phoneNumber")
    @Mapping(target = "userDetails.otherInformation", source = "otherInformation")
    User mapToEntity(UserDto user);
}
