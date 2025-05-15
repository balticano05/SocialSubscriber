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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;

    @Override
    public Flux<UserSubscriptionsResponse> findUserSubscriptionsByUserId(Long id) {

        log.info("Fetching subscriptions for userId={}", id);

        return subscriptionRepository.findAllByUserId(id)
                .map(subscription -> UserSubscriptionsResponse.builder()
                        .serviceName(subscription.getServiceName())
                        .subscriptionId(subscription.getId())
                        .createdAt(subscription.getCreatedAt())
                        .build()
                )
                .doOnComplete(() -> log.info("Completed fetching subscriptions for userId={}", id))
                .doOnError(error -> log.error("Error fetching subscriptions for userId={}", id, error));
    }

    @Override
    public Flux<UserSubscriptionsResponse> findTopThreeBySubscribersCount() {

        log.info("Fetching top 3 subscriptions by subscriber count");

        return subscriptionRepository.findTopThreeBySubscribersCount()
                .map(subscription -> UserSubscriptionsResponse.builder()
                        .serviceName(subscription.getServiceName())
                        .subscriptionId(subscription.getId())
                        .createdAt(subscription.getCreatedAt())
                        .build()
                )
                .doOnComplete(() -> log.info("Completed fetching top 3 subscriptions"))
                .doOnError(error -> log.error("Error fetching top 3 subscriptions", error));
    }

    @Override
    @Transactional
    public Mono<AddSubscriptionResponse> addSubscription(Long userId, AddSubscriptionRequest addSubscriptionRequest) {

        log.info("Adding subscription with serviceName='{}' for userId={}", addSubscriptionRequest.getServiceName(), userId);

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

                    log.info("Saving userSubscription: userId={}, subscriptionId={}", user.getId(), subscription.getId());

                    return userSubscriptionRepository.save(userSubscription)
                            .thenReturn(subscription);
                })

                .flatMap(SubscriptionMapper::mapSubscriptionToAddSubscriptionResponse)
                .doOnSuccess(response -> log.info("Subscription added successfully: subscriptionId={}", response.getSubscriptionId()))
                .doOnError(error -> log.error("Error adding subscription", error));
    }


    @Override
    @Transactional
    public Mono<Long> deleteSubscriptionById(Long sub_id) {

        log.info("Deleting subscription with id={}", sub_id);

        return userSubscriptionRepository.deleteById(sub_id)
                .thenReturn(sub_id)
                .doOnSuccess(id -> log.info("Subscription deleted successfully: id={}", id))
                .doOnError(error -> log.error("Error deleting subscription with id={}", sub_id, error));
    }

}
