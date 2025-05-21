package com.example.userapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserCantPurchaseSubscription extends RuntimeException implements Logg{

    public UserCantPurchaseSubscription(Long id) {
        super(String.format("У пользователя с id %d недостаточно средств", id));
        LOGGER().error(String.format("У пользователя с id %d недостаточно средств", id));
    }

}
