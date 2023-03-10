package com.crud.api.mongo.springboot.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Document(collection = "Users")
@CompoundIndex(def = "{'mobCtryCode':1, 'mobNumber':1, 'email':1}", name = "unique_user", unique = true)
public class User {
    @Id
    private String id;

    @NotNull(message = "First name is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Invalid First name")
    private String firstName;

    @NotNull(message = "Last name is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Invalid Last name")
    private String lastName;

    @NotNull(message = "Mobile country code is required")
    @Pattern(regexp = "^[+][0-9]{1,10}$", message = "Invalid Country code(should start with +, check length <= 10 and only digits)")
    private String mobCtryCode;

    @NotNull(message = "Mobile number is required")
    @Pattern(regexp = "^[0-9]{2,20}$", message = "Invalid Mobile number(check length <= 20 and only digits)")
    private String mobNumber;

    @NotNull(message = "email is required")
    @Pattern(regexp = "^(?=^.{1,195}$)(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\\s*[,]?\\b)*$", message = "Invalid Email")
    private String email;

    @Valid
    private Address address;

    @NotNull(message = "password is required")
    private String password;

    @Pattern(regexp = "^[A-Za-z0-9-]{0,20}+$", message = "Invalid Project name(should be <= 20 characters, only alphanumeric and - symbol)")
    private String projectName;
}
