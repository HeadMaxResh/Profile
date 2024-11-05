package com.t1.profile.profession.service;

import com.t1.profile.profession.exception.ProfessionNotFoundException;
import com.t1.profile.user.dto.UserDto;
import com.t1.profile.user.exception.EntityNotFoundByIdException;
import com.t1.profile.user.mapper.UserMapper;
import com.t1.profile.profession.model.Profession;
import com.t1.profile.user.model.User;
import com.t1.profile.profession.repository.ProfessionRepo;
import com.t1.profile.user.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfessionServiceImpl implements UserProfessionService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProfessionRepo professionRepo;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDto addProfessionToUser(Long userId, Long professionId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundByIdException(User.class, userId));

        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ProfessionNotFoundException("Profession not found with id " + professionId));

        user.setProfession(profession);
        User savedUser = userRepo.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto updateProfessionForUser(Long userId, Long professionId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundByIdException(User.class, userId));

        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ProfessionNotFoundException("Profession not found with id " + professionId));

        user.setProfession(profession);
        User savedUser = userRepo.save(user);
        return userMapper.toDto(savedUser);
    }
}
