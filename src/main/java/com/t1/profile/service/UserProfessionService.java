package com.t1.profile.service;

import com.t1.profile.dto.UserDto;

public interface UserProfessionService {

    UserDto addProfessionToUser(Integer userId, Integer professionId);
    UserDto updateProfessionForUser(Integer userId, Integer professionId);

}
