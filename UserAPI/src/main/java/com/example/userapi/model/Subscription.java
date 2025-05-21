package com.example.userapi.model;

import com.example.userapi.model.enums.StreamingService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StreamingService serviceName;

    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_subscribe")
    private boolean subscribe;

    private BigDecimal price;
    @Column(name = "purchase_count")
    private int purchaseCount;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private User user;
}
