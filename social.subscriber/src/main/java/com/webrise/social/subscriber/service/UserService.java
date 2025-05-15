package com.webrise.social.subscriber.service;

import com.webrise.social.subscriber.dto.request.user.UserCreationRequest;
import com.webrise.social.subscriber.dto.request.user.UserUpdateRequest;
import com.webrise.social.subscriber.dto.response.user.UserCreationResponse;
import com.webrise.social.subscriber.dto.response.user.UserFullInformationResponse;
import com.webrise.social.subscriber.dto.response.user.UserUpdateResponse;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserFullInformationResponse> findUserFullInformationById(Long id);

    Mono<UserCreationResponse> createUser(UserCreationRequest userCreationRequest);

    Mono<UserUpdateResponse> updateUserById(Long id, UserUpdateRequest userUpdateRequest);

    Mono<Long> deleteUserById(Long id);

}