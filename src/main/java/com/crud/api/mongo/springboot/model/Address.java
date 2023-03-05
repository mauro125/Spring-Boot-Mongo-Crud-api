package com.crud.api.mongo.springboot.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Address {
    @NotNull(message = "Address is required")
    @Pattern(regexp = "^[\\w\\-\\s]+$", message = "Invalid address")
    private String address;

    @NotNull(message = "City is required")
    @Pattern(regexp = "^[\\w\\-\\s]+$", message = "Invalid city")
    private String city;

    @NotNull(message = "State is required")
    @Pattern(regexp = "^[\\w\\-\\s]+$", message = "Invalid state")
    private String state;

    @NotNull(message = "Country is required")
    @Pattern(regexp = "^[\\w\\-\\s]+$", message = "Invalid country")
    private String country;
}
