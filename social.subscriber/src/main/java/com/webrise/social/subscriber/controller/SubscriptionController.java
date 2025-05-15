package com.webrise.social.subscriber.controller;

import com.webrise.social.subscriber.dto.request.subscription.AddSubscriptionRequest;
import com.webrise.social.subscriber.dto.response.subscription.AddSubscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users/{id}/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    public Flux<UserSubscriptionsResponse> findUserSubscriptions(){
        subscriptionService.findUserSubscriptionsByUserId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AddSubscriptionResponse> addSubscription(
            @PathVariable Long id,
            @RequestBody AddSubscriptionRequest addSubscriptionRequest){
        subscriptionService.addSubscription(id, addSubscriptionRequest);
    }

    @DeleteMapping("/{sub_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteSubscriptionById(@PathVariable Long sub_id){
        subscriptionService.deleteSubscriptionById(sub_id);
    }

}
