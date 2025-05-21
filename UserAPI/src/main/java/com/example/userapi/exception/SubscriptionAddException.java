package com.example.userapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SubscriptionAddException extends RuntimeException implements Logg{

    public SubscriptionAddException() {
        super("Ошибка добавления, напишите стоимость и/или название сервиса");
        LOGGER().error("Ошибка добавления, напишите стоимость и/или название сервиса");
    }

}
