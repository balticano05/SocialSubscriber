package com.webrise.social.subscriber.service.impl;

import com.webrise.social.subscriber.dto.mapper.UserMapper;
import com.webrise.social.subscriber.dto.request.user.UserCreationRequest;
import com.webrise.social.subscriber.dto.request.user.UserUpdateRequest;
import com.webrise.social.subscriber.dto.response.user.UserCreationResponse;
import com.webrise.social.subscriber.dto.response.user.UserFullInformationResponse;
import com.webrise.social.subscriber.dto.response.user.UserUpdateResponse;
import com.webrise.social.subscriber.entity.User;
import com.webrise.social.subscriber.exception.UserNotFoundException;
import com.webrise.social.subscriber.repository.SubscriptionRepository;
import com.webrise.social.subscriber.repository.UserRepository;
import com.webrise.social.subscriber.repository.UserSubscriptionRepository;
import com.webrise.social.subscriber.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Mono<UserFullInformationResponse> findUserFullInformationById(Long id) {
       return UserMapper.mapUserToUserFullInformationResponse(
               userRepository.findById(id).switchIfEmpty(Mono.error(new UserNotFoundException(id)))
       );
    }

    @Override
    @Transactional
    public Mono<UserCreationResponse> createUser(UserCreationRequest userCreationRequest) {

        User user = User
                .builder()
                .name(userCreationRequest.getName())
                .email(userCreationRequest.getEmail())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mono<User> userMono = userRepository.save(user);

        return UserMapper.mapUserToUserCreationResponse(userMono);
    }

    @Override
    @Transactional
    public Mono<UserUpdateResponse> updateUserById(Long id, UserUpdateRequest userUpdateRequest) {

        Mono<User> updatedUserMono = userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException(id)))
                .flatMap(user -> {
                    if (userUpdateRequest.getName() != null) {
                        user.setName(userUpdateRequest.getName());
                    }
                    if (userUpdateRequest.getEmail() != null) {
                        user.setEmail(userUpdateRequest.getEmail());
                    }
                    user.setUpdatedAt(LocalDateTime.now());
                    return userRepository.save(user);
                });

        return UserMapper.mapUserToUserUpdateResponse(updatedUserMono);
    }

    @Override
    @Transactional
    public Mono<Long> deleteUserById(Long id) {

        userRepository.deleteById(id);

        return Mono.just(id);
    }

}