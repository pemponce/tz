package com.example.userapi.controller;

import com.example.userapi.mapper.SubscriptionMapper;
import com.example.userapi.model.dto.SubscriptionDto;
import com.example.userapi.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{userId}/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final SubscriptionMapper subscriptionMapper;


    @PostMapping
    public ResponseEntity<SubscriptionDto> addSubscription(@PathVariable Long userId, @RequestBody SubscriptionDto dto) {
        var subscription = subscriptionService.addSubscriptionToUserById(dto, userId);
        return ResponseEntity.status(HttpStatus.OK).body(subscription);
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionDto>> getSubscriptions(@PathVariable Long userId) {
        var subscriptions = subscriptionService.getActiveSubscriptionsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(subscriptions);
    }

    @DeleteMapping("/{subId}")
    @Transactional
    public ResponseEntity<SubscriptionDto> deleteSubscription(@PathVariable Long userId, @PathVariable Long subId) {
        var subscription = subscriptionMapper.subscriptionToDto(subscriptionService.getSubscription(subId, userId));
        subscriptionService.deleteUserSubscriptionById(userId, subId);
        return ResponseEntity.status(HttpStatus.OK).body(subscription);
    }

    @PostMapping("/{subId}/renew")
    public ResponseEntity<SubscriptionDto> renewSubscription(@PathVariable Long userId, @PathVariable Long subId) {
        var subscription = subscriptionService.renewSubscriptionForUserById(userId, subId);
        return ResponseEntity.status(HttpStatus.OK).body(subscription);
    }
}
