package com.t1.profile.user.controller;

import com.t1.profile.user.dto.UserDto;
import com.t1.profile.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.create(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    // Обновление пользователя по ID
    @PutMapping("/{id}")
    public UserDto update(@RequestBody UserDto userDto, @PathVariable Integer id) {
        return userService.update(id, userDto);
    }

    @PatchMapping("/{id}")
    public UserDto partialUpdate(@PathVariable Integer id, @RequestBody UserDto userDto) {
        return userService.partialUpdate(id, userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    public UserDto findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("?search={query}")
    public List<UserDto> findByQuery(@RequestParam("query") String query,
        @RequestParam(name = "noTeamId", required = false) Integer noTeamId) {
        return userService.findByQuery(query);
    }

}
