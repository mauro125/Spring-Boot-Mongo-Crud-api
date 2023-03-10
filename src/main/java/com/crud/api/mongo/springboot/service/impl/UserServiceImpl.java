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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
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


    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getAllUsersWithPagination(int pageNum, int pageSize) {
        Pageable paging = PageRequest.of(pageNum, pageSize);
        return userRepository.findAll(paging)
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
        User updatedUser = userUpdateAddPrevValuesIfNull(updatedUserDetails, id);
        LOGGER.debug("Updating user with Id: {}", id);
        userRepository.save(updatedUser);
        return updatedUser.getId();
    }


    private User userUpdateAddPrevValuesIfNull(User newUserDetails, String id) {
        User userFromDB = userRepository.findById(id).orElseThrow(() -> {
            LOGGER.error(MessageCodes.USER_NOT_FOUND.getMessage() + ": {}", id);
            return new ResourceNotFoundException(MessageCodes.USER_NOT_FOUND.getMessage());
        });

        LOGGER.debug(MessageCodes.USING_OLD_VALUES_IF_NULL.getMessage(), id);

        newUserDetails.setId(id);

//        Field[] fields = User.class.getDeclaredFields();

        for (Field field : User.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object newValue = field.get(newUserDetails);
                Object oldValue = field.get(userFromDB);
                if (newValue == null) {
                    field.set(newUserDetails, oldValue);
                }
            } catch (IllegalAccessException e) {
                LOGGER.error("Error accessing field: {}", field.getName(), e);
            }
        }

        return newUserDetails;
    }
}
