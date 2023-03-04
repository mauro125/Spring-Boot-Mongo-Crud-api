package com.crud.api.mongo.springboot.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "Users")
@CompoundIndex(def = "{'mobCtryCode':1, 'mobNumber':1, 'email':1}", name = "unique_user", unique = true)
public class User {
    @Id
    private String id;

    @Pattern(regexp = "^[A-Za-z]+$", message = "Invalid First name")
    private String firstName;
    @Pattern(regexp = "^[A-Za-z]+$", message = "Invalid Last name")
    private String lastName;
    @Pattern(regexp = "^[+][0-9]{1,10}$", message = "Invalid Country code(should start with +, check length <= 10 and only digits)")
    private String mobCtryCode;
    @Pattern(regexp = "^[0-9]{2,20}$", message = "Invalid Mobile number(check length <= 20 and only digits)")
    private String mobNumber;
    @Pattern(regexp = "^(?=^.{1,195}$)(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\\s*[,]?\\b)*$", message = "Invalid Email")
    private String email;
    @Valid
    private Address address;

    public User(String id, String firstName, String lastName, String mobCtryCode, String mobNumber, String email, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobCtryCode = mobCtryCode;
        this.mobNumber = mobNumber;
        this.email = email;
        this.address = address;
    }
}
