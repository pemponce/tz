package com.example.userapi.service.Impl;

import com.example.userapi.controller.UserController;
import com.example.userapi.exception.UserNotFoundException;
import com.example.userapi.exception.UserWithThisUsernameAlreadyExistException;
import com.example.userapi.mapper.UserMapper;
import com.example.userapi.model.User;
import com.example.userapi.model.dto.UserDto;
import com.example.userapi.repository.UserRepository;
import com.example.userapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public UserDto createUser(UserDto user) {
        if (repository.getUserByUsername(user.getUsername()).isPresent()) {
            throw new UserWithThisUsernameAlreadyExistException(user.getUsername());
        }

        var newUser = User.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .funds(user.getFunds())
                .build();

        repository.save(newUser);

        LOGGER.info(String.format("Пользователь %s создал аккаунт", user.getUsername()));

        return new UserDto(newUser.getUsername(), newUser.getEmail(), newUser.getPassword(), newUser.getFunds());
    }

    @Override
    public UserDto editUser(Long id, UserDto userDto) {
        var existingUser = repository.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException(userDto.getUsername()));
        var name = existingUser.getUsername();

        Optional.ofNullable(userDto.getUsername())
                .filter(username -> !username.isBlank())
                .ifPresent(existingUser::setUsername);

        Optional.ofNullable(userDto.getEmail())
                .filter(email -> !email.isBlank())
                .ifPresent(existingUser::setEmail);

        Optional.ofNullable(userDto.getPassword())
                .filter(password -> !password.isBlank())
                .ifPresent(existingUser::setPassword);

        Optional.ofNullable(userDto.getFunds())
                .filter(funds -> funds.compareTo(BigDecimal.ZERO) > 0)
                .ifPresent(existingUser::setFunds);
        repository.save(existingUser);

        LOGGER.info(String.format("Пользователь c id %d изменил данные аккаунта", id));


        return new UserDto(existingUser.getUsername(), existingUser.getEmail(), existingUser.getPassword(), existingUser.getFunds());
    }

    @Override
    public void deleteUser(Long id) {
        var deleteUser = getUser(id).orElseThrow(() -> new UserNotFoundException(id));
        repository.deleteUserById(id);
        LOGGER.info(String.format("Пользователь с id %d и именем %s удален", id, deleteUser.getUsername()));
    }

    @Override
    public Optional<UserDto> getUser(Long id) {
        var user = repository.getUserById(id).orElseThrow(() -> new UserNotFoundException(id));
        return Optional.ofNullable(mapper.userToDto(user));
    }

    @Override
    public User findUser(Long id) {
        return repository.getUserById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public Optional<User> changeFunds(Long id, BigDecimal funds) {
        var user = repository.getUserById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setFunds(funds);
        repository.save(user);
        return Optional.of(user);
    }
}
