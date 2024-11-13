package com.t1.profile.user.service;

import com.t1.profile.user.dto.UserDetailsDto;
import com.t1.profile.user.dto.UserDto;
import com.t1.profile.profession.model.Profession;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDetailsDto findByEmail(String email);

    List<UserDto> findByProfession(Profession profession);

    UserDto findById(Integer id);

}
