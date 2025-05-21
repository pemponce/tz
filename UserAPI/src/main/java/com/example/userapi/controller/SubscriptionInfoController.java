package com.example.userapi.controller;

import com.example.userapi.model.dto.SubscriptionDto;
import com.example.userapi.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionInfoController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/top")
    public ResponseEntity<List<SubscriptionDto>> getTop() {
        var top = subscriptionService.getTopThreeSubscriptions();
        return ResponseEntity.status(HttpStatus.OK).body(top);
    }

}
