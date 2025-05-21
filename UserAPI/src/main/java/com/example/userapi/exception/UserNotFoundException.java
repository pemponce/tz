package com.example.userapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException implements Logg {

    public UserNotFoundException(String username) {
        super(String.format("Пользователь с именем %s не найден", username));
        LOGGER().error(String.format("Пользователь с именем %s не найден", username));
    }

    public UserNotFoundException(Long id) {
        super(String.format("Пользователь с id %d не найден", id));
        LOGGER().error(String.format("Пользователь с id %d не найден", id));
    }

}
