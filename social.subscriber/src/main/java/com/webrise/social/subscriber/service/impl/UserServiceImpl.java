package com.webrise.social.subscriber.service.impl;

import com.webrise.social.subscriber.dto.mapper.UserMapper;
import com.webrise.social.subscriber.dto.request.user.UserCreationRequest;
import com.webrise.social.subscriber.dto.request.user.UserUpdateRequest;
import com.webrise.social.subscriber.dto.response.user.UserCreationResponse;
import com.webrise.social.subscriber.dto.response.user.UserFullInformationResponse;
import com.webrise.social.subscriber.dto.response.user.UserUpdateResponse;
import com.webrise.social.subscriber.entity.User;
import com.webrise.social.subscriber.exception.UserNotFoundException;
import com.webrise.social.subscriber.repository.UserRepository;
import com.webrise.social.subscriber.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Mono<UserFullInformationResponse> findUserFullInformationById(Long id) {

        log.info("Fetching full information for userId={}", id);

        return UserMapper.mapUserToUserFullInformationResponse(
                userRepository.findById(id)
                        .switchIfEmpty(Mono.error(new UserNotFoundException(id)))
                        .doOnSuccess(user -> log.info("User found: id={}", id))
                        .doOnError(error -> log.error("User not found: id={}", id))
        );
    }

    @Override
    @Transactional
    public Mono<UserCreationResponse> createUser(UserCreationRequest userCreationRequest) {

        log.info("Creating user with email='{}'", userCreationRequest.getEmail());

        User user = User.builder()
                .name(userCreationRequest.getName())
                .email(userCreationRequest.getEmail())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mono<User> userMono = userRepository.save(user);

        return UserMapper.mapUserToUserCreationResponse(userMono)
                .doOnSuccess(response -> log.info("User created successfully: id={}", response.getId()))
                .doOnError(error -> log.error("Error creating user", error));
    }

    @Override
    @Transactional
    public Mono<UserUpdateResponse> updateUserById(Long id, UserUpdateRequest userUpdateRequest) {

        log.info("Updating user id={}", id);

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

        return UserMapper.mapUserToUserUpdateResponse(updatedUserMono)
                .doOnSuccess(response -> log.info("User updated successfully: id={}", id))
                .doOnError(error -> log.error("Error updating user id={}", id, error));
    }

    @Override
    @Transactional
    public Mono<Long> deleteUserById(Long id) {

        log.info("Deleting user with id={}", id);

        return userRepository.deleteById(id)
                .thenReturn(id)
                .doOnSuccess(deletedId -> log.info("User deleted successfully: id={}", deletedId))
                .doOnError(error -> log.error("Error deleting user id={}", id, error));
    }

}