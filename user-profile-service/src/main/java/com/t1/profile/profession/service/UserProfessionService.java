package com.t1.profile.profession.service;

import com.t1.profile.user.dto.UserDto;

public interface UserProfessionService {

    UserDto addProfessionToUser(Integer userId, Integer professionId);
    UserDto updateProfessionForUser(Integer userId, Integer professionId);

}
