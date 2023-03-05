package com.crud.api.mongo.springboot.dto;

import com.crud.api.mongo.springboot.model.Address;

public record UserDTO(
        String id,
        String firstName,
        String lastName,
        String mobCtryCode,
        String mobNumber,
        String email,
        Address address,
        String projectName
) {

}
