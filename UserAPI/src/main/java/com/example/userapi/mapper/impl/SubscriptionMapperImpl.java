package com.example.userapi.mapper.impl;

import com.example.userapi.mapper.SubscriptionMapper;
import com.example.userapi.model.Subscription;
import com.example.userapi.model.dto.SubscriptionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
@RequiredArgsConstructor
public class SubscriptionMapperImpl implements SubscriptionMapper {
    @Override
    public SubscriptionDto subscriptionToDto(Subscription subscription) {
        return SubscriptionDto.builder()
                .serviceName(subscription.getServiceName())
                .startDate(subscription.getStartDate())
                .endDate(subscription.getEndDate())
                .isSubscribe(subscription.isSubscribe())
                .price(subscription.getPrice())
                .purchaseCount(subscription.getPurchaseCount())
                .build();
    }
}
