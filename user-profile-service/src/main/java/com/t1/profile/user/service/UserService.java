package com.t1.profile.user.service;

import com.t1.profile.user.dto.UserDto;
import com.t1.profile.profession.model.Profession;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto findByEmail(String email);

    List<UserDto> findByProfession(Profession profession);

    UserDto findById(Integer id);

    void updateUserPhotoPath(Integer userId, String photoPath); // для фотографии

    String getUserPhotoPath(Integer userId); // для получения пути к фотографии
}
