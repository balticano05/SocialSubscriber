package com.webrise.social.subscriber.dto.mapper;

import com.webrise.social.subscriber.dto.response.user.UserCreationResponse;
import com.webrise.social.subscriber.dto.response.user.UserFullInformationResponse;
import com.webrise.social.subscriber.dto.response.user.UserUpdateResponse;
import com.webrise.social.subscriber.entity.User;
import reactor.core.publisher.Mono;

public class UserMapper {

    public static Mono<UserCreationResponse> mapUserToUserCreationResponse(Mono<User> source) {
        return source.map(user -> UserCreationResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build());
    }

    public static Mono<UserUpdateResponse> mapUserToUserUpdateResponse(Mono<User> source) {
        return source.map(user -> UserUpdateResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .updatedAt(user.getUpdatedAt())
                .createdAt(user.getCreatedAt())
                .build());
    }

    public static Mono<UserFullInformationResponse> mapUserToUserFullInformationResponse(Mono<User> source) {
        return source.map(user -> UserFullInformationResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build());
    }

}
