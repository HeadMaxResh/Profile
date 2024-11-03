package com.t1.profile.user.service;

import com.t1.profile.user.dto.UserDto;
import com.t1.profile.user.mapper.UserMapper;
import com.t1.profile.profession.model.Profession;
import com.t1.profile.user.model.User;
import com.t1.profile.user.repository.UserRepo;
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

    @Override
    public UserDto findById(Integer id) {
        return userRepo.findById(id)
                .map(userMapper::toDto)
                .orElse(null);
    }

}
