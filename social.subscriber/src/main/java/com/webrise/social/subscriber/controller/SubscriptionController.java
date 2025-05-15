package com.webrise.social.subscriber.controller;

import com.webrise.social.subscriber.dto.request.subscription.AddSubscriptionRequest;
import com.webrise.social.subscriber.dto.response.subscription.AddSubscriptionResponse;
import com.webrise.social.subscriber.dto.response.subscription.UserSubscriptionsResponse;
import com.webrise.social.subscriber.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users/{id}/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    public Flux<UserSubscriptionsResponse> findUserSubscriptions(@PathVariable Long id) {
        return subscriptionService.findUserSubscriptionsByUserId(id);
    }

    @GetMapping("/top")
    public Flux<UserSubscriptionsResponse> findTopThreeBySubscribersCount() {
        return subscriptionService.findTopThreeBySubscribersCount();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AddSubscriptionResponse> addSubscription(
            @PathVariable Long id,
            @RequestBody AddSubscriptionRequest addSubscriptionRequest) {
        return subscriptionService.addSubscription(id, addSubscriptionRequest);
    }

    @DeleteMapping("/{sub_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Long> deleteSubscriptionById(@PathVariable Long sub_id) {
        return subscriptionService.deleteSubscriptionById(sub_id);
    }

}
