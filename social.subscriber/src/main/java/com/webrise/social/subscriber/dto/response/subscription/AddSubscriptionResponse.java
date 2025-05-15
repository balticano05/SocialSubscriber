package com.webrise.social.subscriber.dto.response.subscription;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddSubscriptionResponse {

    private Long subscriptionId;

    private String serviceName;

    private LocalDateTime createdAt;

}