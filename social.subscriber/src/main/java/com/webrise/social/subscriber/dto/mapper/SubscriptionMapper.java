package com.webrise.social.subscriber.dto.mapper;

import com.webrise.social.subscriber.dto.response.subscription.AddSubscriptionResponse;
import com.webrise.social.subscriber.entity.Subscription;
import reactor.core.publisher.Mono;

public class SubscriptionMapper {

    public static Mono<AddSubscriptionResponse> mapSubscriptionToAddSubscriptionResponse(Subscription subscription) {
        return Mono.just(
                AddSubscriptionResponse.builder()
                        .subscriptionId(subscription.getId())
                        .serviceName(subscription.getServiceName())
                        .createdAt(subscription.getCreatedAt())
                        .build()
        );
    }

}
