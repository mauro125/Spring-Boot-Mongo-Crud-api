package com.crud.api.mongo.springboot.web.impl;

import com.crud.api.mongo.springboot.dto.UserDTO;
import com.crud.api.mongo.springboot.model.User;
import com.crud.api.mongo.springboot.service.impl.UserServiceImpl;
import com.crud.api.mongo.springboot.web.UserInfoController;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserInfoControllerImpl implements UserInfoController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final UserServiceImpl userService;

    public UserInfoControllerImpl(UserServiceImpl userService) {
        this.userService = userService;
    }

//    @GetMapping
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }

//    @GetMapping("{id}")
//    public User getUserById(@PathVariable String id) {
//        return userService.getUserById(id);
//    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public UserDTO getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid User user) {
        String savedUserId = userService.saveUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUserId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @Valid @RequestBody User user) {
        String updatedUserId = userService.updateUser(id, user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(updatedUserId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("{id}")
    public void deleteUserById(@PathVariable String id) {
        userService.deleteUserById(id);
    }
}