package com.t1.profile.controller;

import com.t1.profile.dto.UserDto;
import com.t1.profile.service.UserProfessionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/profession/{professionId}")
public class UserProfessionController {

    @Autowired
    private UserProfessionServiceImpl userProfessionService;

    @PostMapping("/add")
    public ResponseEntity<UserDto> addProfessionToUser(
            @PathVariable Integer userId,
            @PathVariable Integer professionId
    ) {
        UserDto updatedUser = userProfessionService.addProfessionToUser(userId, professionId);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/")
    public ResponseEntity<UserDto> updateProfessionForUser(
            @PathVariable Integer userId,
            @PathVariable Integer professionId) {
        UserDto updatedUser = userProfessionService.updateProfessionForUser(userId, professionId);
        return ResponseEntity.ok(updatedUser);
    }

}
