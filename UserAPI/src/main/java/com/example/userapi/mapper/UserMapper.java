package com.example.userapi.mapper;

import com.example.userapi.model.User;
import com.example.userapi.model.dto.UserDto;

import java.util.Optional;

public interface UserMapper {
    UserDto userToDto(User user);
}
