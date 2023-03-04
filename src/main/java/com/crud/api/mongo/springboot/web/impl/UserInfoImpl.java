package com.crud.api.mongo.springboot.web.impl;

import com.crud.api.mongo.springboot.web.UserInfo;
import com.crud.api.mongo.springboot.model.User;
import com.crud.api.mongo.springboot.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserInfoImpl implements UserInfo {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final UserServiceImpl userService;

    public UserInfoImpl(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody @Valid User user) {
        User savedUser = userService.saveUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @Valid @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(updatedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable String id) {
        userService.deleteUserById(id);
    }
}