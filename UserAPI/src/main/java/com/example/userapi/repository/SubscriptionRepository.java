package com.example.userapi.repository;

import com.example.userapi.model.Subscription;
import com.example.userapi.model.enums.StreamingService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> getSubscriptionByIdAndUserId(Long subscriptionId, Long userId);
    void deleteSubscriptionByIdAndUserId(Long subscriptionId, Long userId);
    List<Subscription> getAllBySubscribeIsTrueAndUserId(Long id);
    Optional<Subscription> getSubscriptionByServiceName(StreamingService serviceName);

    @Query("SELECT s.serviceName, SUM(s.purchaseCount) as total " +
            "FROM Subscription s " +
            "GROUP BY s.serviceName " +
            "ORDER BY total DESC")
    List<Object[]> findTopServiceNames(Pageable pageable);

}
