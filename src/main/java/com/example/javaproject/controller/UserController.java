package com.example.javaproject.controller;

import com.example.javaproject.exception.definition.EmailAlreadyUsedException;
import com.example.javaproject.mapper.UserMapper;
import com.example.javaproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.javaproject.model.UserType;
import com.example.javaproject.dto.UserDto;
import com.example.javaproject.service.UserService;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) throws EmailAlreadyUsedException, NoSuchAlgorithmException {
        return ResponseEntity
                .ok()
                .body(userService.createUser(userDto, UserType.USER));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long id) throws NoSuchAlgorithmException {
        return ResponseEntity
                .ok()
                .body(userService.updateUser(userMapper.mapToEntity(userDto), id, UserType.USER));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserDto> delete(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(@PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(userService.findUserById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> get() {
        return ResponseEntity
                .ok()
                .body(userService.getAllUsers());
    }
}
