package com.webrise.social.subscriber.controller;

import com.webrise.social.subscriber.dto.request.user.UserCreationRequest;
import com.webrise.social.subscriber.dto.request.user.UserUpdateRequest;
import com.webrise.social.subscriber.dto.response.user.UserCreationResponse;
import com.webrise.social.subscriber.dto.response.user.UserFullInformationResponse;
import com.webrise.social.subscriber.dto.response.user.UserUpdateResponse;
import com.webrise.social.subscriber.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;

    @GetMapping("/{id}")
    public Mono<UserFullInformationResponse> findUserFullInformationById(@PathVariable Long id) {
        return userService.findUserFullInformationById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserCreationResponse> createUser(@RequestBody UserCreationRequest userCreationRequest) {
        return userService.createUser(userCreationRequest);
    }

    @PutMapping("/{id}")
    public Mono<UserUpdateResponse> updateUserById(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUserById(id, userUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public Mono<Long> deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }

}