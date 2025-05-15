package com.webrise.social.subscriber.repository;

import org.reactivestreams.Subscription;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends R2dbcRepository<Subscription, Long> {
}
