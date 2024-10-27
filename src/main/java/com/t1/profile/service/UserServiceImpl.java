package com.t1.profile.service;

import com.t1.profile.dto.UserDto;
import com.t1.profile.mapper.UserMapper;
import com.t1.profile.model.Profession;
import com.t1.profile.model.User;
import com.t1.profile.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.toDtoList(userRepo.findAll());
    }

    @Override
    public UserDto findByEmail(String email) {
        User user =  userRepo.findByEmail(email);
        return user != null ? userMapper.toDto(user) : null;
    }

    @Override
    public List<UserDto> findByProfession(Profession profession) {
        List<User> users = userRepo.findByProfession(profession);
        return userMapper.toDtoList(users);
    }

}
