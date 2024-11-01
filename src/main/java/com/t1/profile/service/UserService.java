package com.t1.profile.service;

import com.t1.profile.dto.UserDto;
import com.t1.profile.model.Profession;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto findByEmail(String email);

    List<UserDto> findByProfession(Profession profession);

    UserDto findById(Integer id);

    void uploadUserPhoto(Integer userId, MultipartFile file) throws IOException; // для фотографии

}
