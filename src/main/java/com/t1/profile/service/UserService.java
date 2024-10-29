package com.t1.profile.service;

import com.t1.profile.dto.UserDto;
import com.t1.profile.model.Profession;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto findByEmail(String email);

    List<UserDto> findByProfession(Profession profession);

    UserDto findById(Integer id);

}
