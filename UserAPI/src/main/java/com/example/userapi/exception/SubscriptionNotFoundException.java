package com.example.userapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SubscriptionNotFoundException extends RuntimeException implements Logg{

    public SubscriptionNotFoundException(Long id) {
        super(String.format("Сервис с id %d не был найден или его не существует", id));
        LOGGER().error(String.format("Сервис с id %d не был найден или его не существует", id));
    }

}
