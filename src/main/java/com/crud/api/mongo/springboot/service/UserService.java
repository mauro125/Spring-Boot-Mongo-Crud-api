package com.crud.api.mongo.springboot.service;

import com.crud.api.mongo.springboot.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(String id);
    String saveUser(User user);
    void deleteUserById(String id);
    String updateUser(String id, User user);
}
