package com.crud.api.mongo.springboot.service.impl;


import com.crud.api.mongo.springboot.constants.MessageCodes;
import com.crud.api.mongo.springboot.constants.exception.ResourceNotFoundException;
import com.crud.api.mongo.springboot.model.User;
import com.crud.api.mongo.springboot.repository.UserRepository;
import com.crud.api.mongo.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> {
                    LOGGER.error(MessageCodes.USER_NOT_FOUND.getMessage() + ": {}", id);
                    return new ResourceNotFoundException(MessageCodes.USER_NOT_FOUND.getMessage());
                }
        );
    }

    public String saveUser(User user) {
        LOGGER.debug("Saving user: {}", user);
        userRepository.insert(user);
        return user.getId();
    }

    public void deleteUserById(String id) {
        getUserById(id);
        LOGGER.debug("Deleting user with Id: {}", id);
        userRepository.deleteById(id);
    }

    public String updateUser(String id, User updatedUser) {
        deleteUserById(id);
        LOGGER.debug("Updating user with Id: {}", id);
        updatedUser.setId(id);
        userRepository.save(updatedUser);
        return updatedUser.getId();
    }
}
