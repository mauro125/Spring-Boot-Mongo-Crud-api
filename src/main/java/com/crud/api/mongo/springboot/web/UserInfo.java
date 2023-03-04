package com.crud.api.mongo.springboot.web;

import com.crud.api.mongo.springboot.model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public interface UserInfo {

    @GetMapping("/users")
    List<User> getAllUsers();

    @GetMapping("/users/{id}")
    Optional<User> getUserById(@PathVariable String id);

    @PostMapping("/users")
    ResponseEntity<Object> createUser(@Valid @RequestBody User user);

    @PutMapping("/users/{id}")
    ResponseEntity<User> updateUser(@PathVariable String id, @Valid @RequestBody User user);

    @DeleteMapping("/users/{id}")
    void deleteUserById(@PathVariable String id);

}
