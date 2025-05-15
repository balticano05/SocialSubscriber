package com.webrise.social.subscriber.dto.request.subscription;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddSubscriptionRequest {

    @NotBlank(message = "Service name must not be blank")
    @Size(max = 255, message = "Service name must be at most 255 characters")
    private String serviceName;

}
