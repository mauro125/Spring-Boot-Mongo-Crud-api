package com.crud.api.mongo.springboot.constants.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ErrorDetails {

    private LocalDateTime timestamp;
    private List<String> errorMessages;
    private String details;
    private int errorCode;
}
