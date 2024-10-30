package com.t1.profile.controller;

import com.t1.profile.dto.UserDto;
import com.t1.profile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000") // Добавьте эту строку
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    List<UserDto> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    UserDto getUser(@PathVariable Integer id) {
        return userService.findById(id);
    }

}
