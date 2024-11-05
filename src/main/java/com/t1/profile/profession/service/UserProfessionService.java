package com.t1.profile.profession.service;

import com.t1.profile.user.dto.UserDto;

public interface UserProfessionService {

    UserDto addProfessionToUser(Long userId, Long professionId);

    UserDto updateProfessionForUser(Long userId, Long professionId);
}
