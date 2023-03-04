package com.crud.api.mongo.springboot.service.impl;


import com.crud.api.mongo.springboot.constants.MessageCodes;
import com.crud.api.mongo.springboot.model.User;
import com.crud.api.mongo.springboot.repository.UserRepository;
import com.crud.api.mongo.springboot.service.UserService;
import com.crud.api.mongo.springboot.constants.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    //    @Autowired
    private final UserRepository userRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            LOGGER.error(MessageCodes.USER_NOT_FOUND.getMessage() + ": {}", id);
            throw new UserNotFoundException(MessageCodes.USER_NOT_FOUND.getMessage());
        }
        return user;
    }

    public User saveUser(User user) {
        LOGGER.debug("Saving user: {}", user);
        return userRepository.insert(user);
    }

    public void deleteUserById(String id) {
        getUserById(id);
        LOGGER.debug("Deleting user with Id: {}", id);
        userRepository.deleteById(id);
    }

    public User updateUser(String id, User updatedUser) {
        deleteUserById(id);
//        updatedUser.setId(id);
        LOGGER.debug("Updating user with Id: {}", id);
        return userRepository.save(updatedUser);
    }
}
