package com.webrise.social.subscriber.controller;

import com.webrise.social.subscriber.dto.request.UserCreationRequest;
import com.webrise.social.subscriber.dto.request.UserUpdateRequest;
import com.webrise.social.subscriber.dto.response.UserCreationResponse;
import com.webrise.social.subscriber.dto.response.UserFullInformationResponse;
import com.webrise.social.subscriber.dto.response.UserUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    public UserService userService;

    @GetMapping("/{id}")
    public Mono<UserFullInformationResponse> findUserFullInformationById(@PathVariable long id) {
        return userService.findUserFullInformationById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserCreationResponse> createUser(@RequestBody UserCreationRequest userCreationRequest) {
        return userService.createUser(userCreationRequest);
    }

    @PutMapping("/{id}")
    public Mono<UserUpdateResponse> updateUserById(@PathVariable long id, @RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUserById(id, userUpdateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteUserById(@PathVariable long id) {
        return userService.deleteUserById(id);
    }

}