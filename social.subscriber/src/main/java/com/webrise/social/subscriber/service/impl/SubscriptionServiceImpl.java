package com.webrise.social.subscriber.service.impl;

import com.webrise.social.subscriber.dto.mapper.SubscriptionMapper;
import com.webrise.social.subscriber.dto.request.subscription.AddSubscriptionRequest;
import com.webrise.social.subscriber.dto.response.subscription.AddSubscriptionResponse;
import com.webrise.social.subscriber.dto.response.subscription.UserSubscriptionsResponse;
import com.webrise.social.subscriber.entity.Subscription;
import com.webrise.social.subscriber.entity.User;
import com.webrise.social.subscriber.entity.UserSubscription;
import com.webrise.social.subscriber.exception.UserNotFoundException;
import com.webrise.social.subscriber.repository.SubscriptionRepository;
import com.webrise.social.subscriber.repository.UserRepository;
import com.webrise.social.subscriber.repository.UserSubscriptionRepository;
import com.webrise.social.subscriber.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;

    @Override
    public Flux<UserSubscriptionsResponse> findUserSubscriptionsByUserId(Long id) {
        return subscriptionRepository.findAllByUserId(id)
                .map(subscription -> UserSubscriptionsResponse.builder()
                        .serviceName(subscription.getServiceName())
                        .subscriptionId(subscription.getId())
                        .createdAt(subscription.getCreatedAt())
                        .build()
                );
    }

    @Override
    public Flux<UserSubscriptionsResponse> findTopThreeBySubscribersCount() {
        return subscriptionRepository.findTopThreeBySubscribersCount()
                .map(subscription -> UserSubscriptionsResponse.builder()
                        .serviceName(subscription.getServiceName())
                        .subscriptionId(subscription.getId())
                        .createdAt(subscription.getCreatedAt())
                        .build()
                );
    }

    @Override
    @Transactional
    public Mono<AddSubscriptionResponse> addSubscription(Long userId, AddSubscriptionRequest addSubscriptionRequest) {

        Mono<User> userMono = userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new UserNotFoundException(userId)));

        Mono<Subscription> subscriptionMono = Mono.just(
                Subscription.builder()
                        .serviceName(addSubscriptionRequest.getServiceName())
                        .createdAt(LocalDateTime.now())
                        .build()
        ).flatMap(subscriptionRepository::save);

        return Mono.zip(userMono, subscriptionMono)
                .flatMap(tuple -> {
                    User user = tuple.getT1();
                    Subscription subscription = tuple.getT2();

                    UserSubscription userSubscription = new UserSubscription();
                    userSubscription.setUserId(user.getId());
                    userSubscription.setSubscriptionId(subscription.getId());

                    return userSubscriptionRepository.save(userSubscription)
                            .thenReturn(subscription);
                })

                .flatMap(SubscriptionMapper::mapSubscriptionToAddSubscriptionResponse);
    }

    @Override
    @Transactional
    public Mono<Long> deleteSubscriptionById(Long sub_id) {

        userSubscriptionRepository.deleteById(sub_id);

        return Mono.just(sub_id);
    }

}
