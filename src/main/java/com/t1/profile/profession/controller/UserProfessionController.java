package com.t1.profile.profession.controller;

import com.t1.profile.user.dto.UserDto;
import com.t1.profile.profession.service.UserProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/profession/{professionId}")
public class UserProfessionController {

    @Autowired
    private UserProfessionService userProfessionService;

    @PostMapping("/add")
    public ResponseEntity<UserDto> addProfessionToUser(
            @PathVariable Long userId,
            @PathVariable Long professionId
    ) {
        UserDto updatedUser = userProfessionService.addProfessionToUser(userId, professionId);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateProfessionForUser(
            @PathVariable Long userId,
            @PathVariable Long professionId) {
        UserDto updatedUser = userProfessionService.updateProfessionForUser(userId, professionId);
        return ResponseEntity.ok(updatedUser);
    }

}
