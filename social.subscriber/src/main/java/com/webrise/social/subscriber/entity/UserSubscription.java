package com.webrise.social.subscriber.entity;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("users_subscriptions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSubscription {

    @Column("user_id")
    private Long userId;

    @Column("subscription_id")
    private Long subscriptionId;

}