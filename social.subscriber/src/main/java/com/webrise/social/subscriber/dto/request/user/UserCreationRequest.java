package com.webrise.social.subscriber.dto.request.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationRequest {

    private String name;

    private String email;

}