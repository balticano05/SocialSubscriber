package com.webrise.social.subscriber.service;

import com.webrise.social.subscriber.dto.request.subscription.AddSubscriptionRequest;
import com.webrise.social.subscriber.dto.response.subscription.AddSubscriptionResponse;
import com.webrise.social.subscriber.dto.response.subscription.UserSubscriptionsResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubscriptionService {

    Flux<UserSubscriptionsResponse> findUserSubscriptionsByUserId(Long id);

    Flux<UserSubscriptionsResponse> findTopThreeBySubscribersCount();

    Mono<AddSubscriptionResponse> addSubscription(Long id, AddSubscriptionRequest addSubscriptionRequest);

    Mono<Long> deleteSubscriptionById(Long sub_id);

}
