package com.t1.profile.user.service;

import com.t1.profile.user.dto.UserDto;
import com.t1.profile.user.exception.ResourceNotFoundException;
import com.t1.profile.user.mapper.UserMapper;
import com.t1.profile.profession.model.Profession;
import com.t1.profile.user.model.User;
import com.t1.profile.user.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    //далее код для фото

    @Override
    public void updateUserPhotoPath(Integer userId, String photoPath) {
        User user = userRepo.findById(userId).orElseThrow(() ->
                new RuntimeException("Пользователь не найден с ID: " + userId));

        // Обновляем путь к фото профиля
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
        return user.getPhotoPath(); // Возвращаем путь к фото из объекта пользователя
    }

}
