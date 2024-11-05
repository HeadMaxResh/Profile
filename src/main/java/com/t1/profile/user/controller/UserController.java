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
    public UserDto getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    // Обновление пользователя по ID
    @PutMapping("/{id}")
    public UserDto update(@RequestBody UserDto userDto, @PathVariable Long id) {
        return userService.update(id, userDto);
    }

    @PatchMapping("/{id}")
    public UserDto partialUpdate(@PathVariable Long id, @RequestBody UserDto userDto) {
        return userService.partialUpdate(id, userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /*
    * TODO Подумать над тем, как это переписать, пока у меня нет ответа, на то, как это сделать нормально
    *     по-хорошему наверное стоит подключить elasticsearch
    * */
    @GetMapping("?search={query}&noTeamId={noTeamId}")
    public List<UserDto> findByQuery(@RequestParam("query") String query,
        @RequestParam(name = "noTeamId", required = false) Long noTeamId) {
        if (noTeamId != null) {
            return userService.findByQueryAndNoTeamId(query, noTeamId);
        }
        return userService.findByQuery(query);
    }

}
