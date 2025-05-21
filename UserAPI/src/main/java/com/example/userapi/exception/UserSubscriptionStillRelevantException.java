package com.example.userapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserSubscriptionStillRelevantException extends RuntimeException implements Logg{

    public UserSubscriptionStillRelevantException(Long userId, Long subId) {
        super(String.format("У пользователя с id %d уже есть подписка на сервис с id %d", userId, subId));
        LOGGER().error(String.format("У пользователя с id %d уже есть подписка на сервис с id %d", userId, subId));
    }

}
