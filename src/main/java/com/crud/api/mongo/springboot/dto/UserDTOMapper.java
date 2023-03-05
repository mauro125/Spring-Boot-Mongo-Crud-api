package com.crud.api.mongo.springboot.dto;

import com.crud.api.mongo.springboot.model.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getMobCtryCode(),
                user.getMobNumber(),
                user.getAddress(),
                user.getProjectName()
        );
    }

}
