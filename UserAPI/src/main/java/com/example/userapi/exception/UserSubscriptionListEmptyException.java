package com.example.userapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserSubscriptionListEmptyException extends RuntimeException implements Logg {

    public UserSubscriptionListEmptyException(Long id) {
        super(String.format("Список подписок пользователя с id %d пуст!", id));
        LOGGER().warn(String.format("Список подписок пользователя с id %d пуст!", id));
    }

}
