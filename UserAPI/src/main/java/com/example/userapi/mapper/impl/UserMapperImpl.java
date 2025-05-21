package com.example.userapi.mapper.impl;

import com.example.userapi.mapper.UserMapper;
import com.example.userapi.model.User;
import com.example.userapi.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDto userToDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .funds(user.getFunds())
                .build();
    }
}
