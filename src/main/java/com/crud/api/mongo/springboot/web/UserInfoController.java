package com.crud.api.mongo.springboot.web;

import com.crud.api.mongo.springboot.dto.UserDTO;
import com.crud.api.mongo.springboot.model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public interface UserInfoController {

    @GetMapping
    List<UserDTO> getAllUsers();

    @GetMapping("/all-with-pagination")
    List<UserDTO> getPaginatedUsers(@RequestParam int pageNum, @RequestParam int pageSize);

    @GetMapping("{id}")
    UserDTO getUserById(@PathVariable String id);

    @PostMapping
    ResponseEntity<Object> createUser(@Valid @RequestBody User user);

    @PutMapping("{id}")
    ResponseEntity<User> updateUser(@PathVariable String id, @Valid @RequestBody User user);

    @DeleteMapping("{id}")
    void deleteUserById(@PathVariable String id);

}
