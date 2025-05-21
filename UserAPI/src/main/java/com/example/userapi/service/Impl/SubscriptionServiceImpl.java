package com.example.userapi.service.Impl;

import com.example.userapi.controller.UserController;
import com.example.userapi.exception.*;
import com.example.userapi.mapper.SubscriptionMapper;
import com.example.userapi.mapper.UserMapper;
import com.example.userapi.model.Subscription;
import com.example.userapi.model.dto.SubscriptionDto;
import com.example.userapi.model.enums.StreamingService;
import com.example.userapi.repository.SubscriptionRepository;
import com.example.userapi.service.SubscriptionService;
import com.example.userapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final SubscriptionRepository repository;
    private final SubscriptionMapper subscriptionMapper;
    private final UserMapper userMapper;
    private final UserService userService;

    @Override
    public SubscriptionDto addSubscriptionToUserById(SubscriptionDto subscription, Long userId) {
        var userOptional = userService.getUser(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        var user = userService.findUser(userId);

        boolean alreadySubscribed = user.getSubscriptions().stream()
                .anyMatch(s -> s.getServiceName() == subscription.getServiceName());

        if (alreadySubscribed) {
            throw new UserSubscriptionAlreadyExist(subscription.getServiceName().name());
        } else if (subscription.getServiceName() == null || subscription.getPrice() == null) {
            throw new SubscriptionAddException();
        }

        var userDto = userMapper.userToDto(user);
        var newSubscription = Subscription.builder()
                .serviceName(subscription.getServiceName())
                .subscribe(false)
                .price(subscription.getPrice())
                .user(user)
                .build();

        repository.save(newSubscription);

        LOGGER.info(String.format("Подписка %s успешно добавлена пользователю с id %d.", subscription.getServiceName(), userId));

        return new SubscriptionDto(subscription.getServiceName(),
                subscription.getStartDate(), subscription.getEndDate()
                , subscription.isSubscribe(), subscription.getPrice(),
                subscription.getPurchaseCount(), userDto);
    }

    @Override
    public Subscription getSubscription(Long subscriptionId, Long userId) {
        return repository.getSubscriptionByIdAndUserId(subscriptionId, userId).orElseThrow(() -> new SubscriptionNotFoundException(subscriptionId));
    }

    @Override
    public SubscriptionDto renewSubscriptionForUserById(Long userId, Long subscriptionId) {
        var user = userService.findUser(userId);
        var subscription = getSubscription(subscriptionId, userId);

        if (subscription.isSubscribe()) {
            throw new UserSubscriptionStillRelevantException(userId, subscriptionId);
        }

        var userFunds = user.getFunds();
        var subscriptionPrice = subscription.getPrice();

        if (subscriptionPrice.compareTo(userFunds) > 0) {
            throw new UserCantPurchaseSubscription(userId);
        }

        userService.changeFunds(userId, userFunds.subtract(subscriptionPrice));

        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusMonths(1));
        subscription.setSubscribe(true);
        subscription.setPurchaseCount(subscription.getPurchaseCount()+1);

        repository.save(subscription);

        LOGGER.info(String.format("Пользователь с id %d успешно продлил подписку %s до %s. Остаток средств: %s.",
                userId, subscription.getServiceName(), subscription.getEndDate(), user.getFunds().subtract(subscriptionPrice)));


        return new SubscriptionDto(subscription.getServiceName(),
                subscription.getStartDate(), subscription.getEndDate()
                , subscription.isSubscribe(), subscription.getPrice(),
                subscription.getPurchaseCount(), userMapper.userToDto(subscription.getUser()));
    }

    @Override
    public List<SubscriptionDto> getActiveSubscriptionsByUserId(Long id) {
        List<SubscriptionDto> lst = new ArrayList<>();
        if (userService.getUser(id).isPresent()) {
            SubscriptionDto dto;
            for (Subscription subscription : repository.getAllBySubscribeIsTrueAndUserId(id)) {
                dto = subscriptionMapper.subscriptionToDto(subscription);
                dto.setUser(userMapper.userToDto(userService.findUser(id)));
                lst.add(dto);
            }
            if (lst.size() == 0) {
                throw new UserSubscriptionListEmptyException(id);
            }
        }
        LOGGER.info(String.format("Получены все активные подписки пользователя с id %d. Количество: %d", id, lst.size()));

        return lst;
    }


    @Override
    public void deleteUserSubscriptionById(Long userId, Long subscriptionId) {
        userService.findUser(userId);
        getSubscription(subscriptionId, userId);

        repository.deleteSubscriptionByIdAndUserId(subscriptionId,userId);
        LOGGER.info(String.format("Подписка с id %d пользователя с id %d была успешно удалена.", subscriptionId, userId));
    }

    @Override
    public List<SubscriptionDto> getTopThreeSubscriptions() {
        Pageable topThree = PageRequest.of(0, 3);
        List<Object[]> topServices = repository.findTopServiceNames(topThree);

        List<SubscriptionDto> result = new ArrayList<>();

        for (Object[] row : topServices) {
            StreamingService serviceEnum = (StreamingService) row[0];
            Long totalPurchaseCount = (Long) row[1];
            SubscriptionDto dto = SubscriptionDto.builder()
                    .serviceName(StreamingService.valueOf(serviceEnum.name()))
                    .purchaseCount(totalPurchaseCount.intValue())
                    .build();
            result.add(dto);
        }

        LOGGER.info("Получены топ-3 сервиса по количеству покупок.");
        return result;
    }

}
