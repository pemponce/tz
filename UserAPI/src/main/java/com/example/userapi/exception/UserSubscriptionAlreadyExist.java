package com.example.userapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserSubscriptionAlreadyExist extends RuntimeException implements Logg{

    public UserSubscriptionAlreadyExist(String serviceName) {
        super(String.format("Пользователь уже имеет данный сервис(%s) в списке подписок", serviceName));
        LOGGER().error(String.format("Пользователь уже имеет данный сервис(%s) в списке подписок", serviceName));
    }

}
