package com.example.userapi.mapper;

import com.example.userapi.model.Subscription;
import com.example.userapi.model.dto.SubscriptionDto;

public interface SubscriptionMapper {
    SubscriptionDto subscriptionToDto(Subscription subscription);
}
