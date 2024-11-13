package com.t1.profile.user.controller;

import com.t1.profile.user.dto.UserDetailsDto;
import com.t1.profile.user.dto.UserDto;
import com.t1.profile.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
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

    @GetMapping("/by-email")
    public UserDetailsDto getUserByEmail(@RequestParam String email) {
        return userService.findByEmail(email);
    }

}
