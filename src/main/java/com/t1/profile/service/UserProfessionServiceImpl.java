package com.t1.profile.service;

import com.t1.profile.dto.UserDto;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.model.Profession;
import com.t1.profile.model.User;
import com.t1.profile.repository.ProfessionRepo;
import com.t1.profile.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfessionServiceImpl implements UserProfessionService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProfessionRepo professionRepo;

    @Override
    public UserDto addProfessionToUser(Integer userId, Integer professionId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ResourceNotFoundException("Profession not found with id " + professionId));

        user.setProfession(profession);
        return new UserDto(userRepo.save(user));
    }

    @Override
    public UserDto updateProfessionForUser(Integer userId, Integer professionId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + professionId));

        user.setProfession(profession);
        return new UserDto(userRepo.save(user));
    }
}
