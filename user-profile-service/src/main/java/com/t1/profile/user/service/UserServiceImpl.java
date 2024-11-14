package com.t1.profile.user.service;

import com.t1.profile.user.dto.UserDetailsDto;
import com.t1.profile.user.dto.UserDto;
import com.t1.profile.user.mapper.UserDetailsMapper;
import com.t1.profile.user.mapper.UserMapper;
import com.t1.profile.profession.model.Profession;
import com.t1.profile.user.model.User;
import com.t1.profile.user.repository.UserRepo;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDetailsMapper userDetailsMapper;

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.toDtoList(userRepo.findAll());
    }

    @Override
    public UserDetailsDto findByEmail(String email) {
        User user =  userRepo.findByEmail(email);
        return user != null ? userDetailsMapper.toDto(user) : null;
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

    @Override
    public void updateUserPhotoPath(Integer userId, String photoPath) {
        User user = userRepo.findById(userId).orElseThrow(() ->
                new RuntimeException("Пользователь не найден с ID: " + userId));


        user.setPhotoPath(photoPath);
        userRepo.save(user);
    }

    private boolean isImageFile(String fileName) {
        String[] extensions = { "jpg", "jpeg", "png", "gif" };
        for (String ext : extensions) {
            if (fileName.toLowerCase().endsWith("." + ext)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getUserPhotoPath(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден с ID: " + userId));
        return user.getPhotoPath();
    }
}
