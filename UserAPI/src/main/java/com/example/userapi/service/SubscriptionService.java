package com.example.userapi.service;

import com.example.userapi.model.Subscription;
import com.example.userapi.model.dto.SubscriptionDto;

import java.util.List;

public interface SubscriptionService {
    SubscriptionDto addSubscriptionToUserById(SubscriptionDto subscription, Long userId);
    Subscription getSubscription(Long subscriptionId, Long userId);
    SubscriptionDto renewSubscriptionForUserById(Long userId, Long subscriptionId);
    List<SubscriptionDto> getActiveSubscriptionsByUserId(Long id);
    void deleteUserSubscriptionById(Long userId, Long subscriptionId);
    List<SubscriptionDto> getTopThreeSubscriptions();

}
