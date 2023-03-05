package com.crud.api.mongo.springboot.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageCodes {
    USER_NOT_FOUND("User not found"),
    INVALID_FNAME("Invalid First name"),
    INVALID_LNAME("Invalid Last name"),
    USING_OLD_VALUES_IF_NULL("Updating User values with previous information if null for id: {}");

    private final String message;
}
