package com.crud.api.mongo.springboot.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageCodes {
    USER_NOT_FOUND("User not found");

    private final String message;
}
