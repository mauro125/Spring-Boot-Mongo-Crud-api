package com.crud.api.mongo.springboot.service.impl;


import com.crud.api.mongo.springboot.constants.MessageCodes;
import com.crud.api.mongo.springboot.constants.exception.ResourceNotFoundException;
import com.crud.api.mongo.springboot.dto.UserDTO;
import com.crud.api.mongo.springboot.dto.UserDTOMapper;
import com.crud.api.mongo.springboot.model.User;
import com.crud.api.mongo.springboot.repository.UserRepository;
import com.crud.api.mongo.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    //    @Autowired
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public UserServiceImpl(UserRepository userRepository, UserDTOMapper userDTOMapper) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
    }

//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    public User getUserById(String id) {
//        return userRepository.findById(id).orElseThrow(() -> {
//                    LOGGER.error(MessageCodes.USER_NOT_FOUND.getMessage() + ": {}", id);
//                    return new ResourceNotFoundException(MessageCodes.USER_NOT_FOUND.getMessage());
//                }
//        );
//    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(String id) {
        return userRepository.findById(id)
                .map(userDTOMapper)
                .orElseThrow(() -> {
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

    public String updateUser(String id, User updatedUserDetails) {
        User updatedUser = userUpdaterIfNullAddPrvValues(updatedUserDetails, id);
        deleteUserById(id);
        LOGGER.debug("Updating user with Id: {}", id);
        userRepository.save(updatedUser);
        return updatedUser.getId();
    }

    private User userUpdaterIfNullAddPrvValues(User newUserDetails, String id) {
        User userFromDB = userRepository.findById(id).orElseThrow(() -> {
            LOGGER.error(MessageCodes.USER_NOT_FOUND.getMessage() + ": {}", id);
            return new ResourceNotFoundException(MessageCodes.USER_NOT_FOUND.getMessage());
        });
        LOGGER.debug(MessageCodes.USING_OLD_VALUES_IF_NULL.getMessage(), id);
        newUserDetails.setId(id);
        if (newUserDetails.getFirstName() == null) {
            newUserDetails.setFirstName(userFromDB.getFirstName());
        }
        if (newUserDetails.getLastName() == null) {
            newUserDetails.setLastName(userFromDB.getLastName());
        }
        if (newUserDetails.getEmail() == null) {
            newUserDetails.setEmail(userFromDB.getEmail());
        }
        if (newUserDetails.getMobCtryCode() == null) {
            newUserDetails.setMobCtryCode(userFromDB.getMobCtryCode());
        }
        if (newUserDetails.getMobNumber() == null) {
            newUserDetails.setMobNumber(userFromDB.getMobNumber());
        }
        if (newUserDetails.getAddress() == null) {
            newUserDetails.setAddress(userFromDB.getAddress());
        }
        if (newUserDetails.getPassword() == null) {
            newUserDetails.setPassword(userFromDB.getPassword());
        }
        if (newUserDetails.getProjectName() == null) {
            newUserDetails.setProjectName(userFromDB.getProjectName());
        }

        return newUserDetails;
    }
}
