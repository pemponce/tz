package com.example.userapi.service;

import com.example.userapi.model.User;
import com.example.userapi.model.dto.UserDto;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserService {

    UserDto createUser(UserDto user);
    UserDto editUser(Long id, UserDto userDto);
    void deleteUser(Long id);
    Optional<UserDto> getUser(Long id);
    User findUser(Long id);
    Optional<User> changeFunds(Long id, BigDecimal funds);
}
