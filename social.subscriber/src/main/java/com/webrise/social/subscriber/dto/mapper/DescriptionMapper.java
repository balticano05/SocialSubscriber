package com.webrise.social.subscriber.dto.mapper;

import com.webrise.social.subscriber.dto.response.subscription.AddSubscriptionResponse;
import com.webrise.social.subscriber.entity.Subscription;
import reactor.core.publisher.Mono;

public class DescriptionMapper {

    public static Mono<AddSubscriptionResponse> mapSubscriptionToAddSubscriptionResponse(Mono<Subscription> source) {
        return source.map(subscription -> AddSubscriptionResponse.builder()
                .subscriptionId(subscription.getId())
                .build());
    }

}
