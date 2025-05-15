package com.webrise.social.subscriber.repository;


import com.webrise.social.subscriber.entity.Subscription;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SubscriptionRepository extends R2dbcRepository<Subscription, Long> {

    Mono<Subscription> findByServiceName(String serviceName);

    @Query("""
        SELECT s.id, s.service_name, s.created_at
        FROM subscriptions s
        JOIN users_subscriptions us ON s.id = us.subscription_id
        WHERE us.user_id = :userId
        """)
    Flux<Subscription> findAllByUserId(Long userId);

    @Query("""
        SELECT s.id, s.service_name, s.created_at
        FROM subscriptions s
        JOIN users_subscriptions us ON s.id = us.subscription_id
        GROUP BY s.id, s.service_name, s.created_at
        ORDER BY COUNT(us.user_id) DESC
        LIMIT 3
        """)
    Flux<Subscription> findTopThreeBySubscribersCount();

}