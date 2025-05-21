package com.example.userapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserWithThisUsernameAlreadyExistException extends RuntimeException implements Logg {

    public UserWithThisUsernameAlreadyExistException(String username) {
        super(String.format("Пользователь с именем %s уже существует ", username));
        LOGGER().error(String.format("Пользователь с именем %s уже существует ", username));
    }

}
