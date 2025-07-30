package com.example.library_system.controllers.mappers;

import com.example.library_system.controllers.dto.UserDTO;
import com.example.library_system.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO userDto);

    UserDTO toDTO(User user);
}
