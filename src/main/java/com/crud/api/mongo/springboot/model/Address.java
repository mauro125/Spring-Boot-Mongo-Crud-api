package com.crud.api.mongo.springboot.model;

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
    @Pattern(regexp = "^[\\w\\-\\s]+$", message = "Invalid address")
    private String address;
    @Pattern(regexp = "^[\\w\\-\\s]+$", message = "Invalid city")
    private String city;
    @Pattern(regexp = "^[\\w\\-\\s]+$", message = "Invalid state")
    private String state;
    @Pattern(regexp = "^[\\w\\-\\s]+$", message = "Invalid country")
    private String country;

}
