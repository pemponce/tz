package com.example.userapi.model.dto;

import com.example.userapi.model.enums.StreamingService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {

    @Enumerated(EnumType.STRING)
    private StreamingService serviceName;

    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_subscribe")
    private boolean isSubscribe;

    private BigDecimal price;
    @Column(name = "purchase_count")
    private int purchaseCount;

    private UserDto user;

}
