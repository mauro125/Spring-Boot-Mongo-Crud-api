package com.crud.api.mongo.springboot.service;

import com.crud.api.mongo.springboot.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(String id);
    User saveUser(User user);
    void deleteUserById(String id);
    User updateUser(String id, User user);
}
