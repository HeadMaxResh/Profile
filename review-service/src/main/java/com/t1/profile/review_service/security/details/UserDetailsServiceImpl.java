package com.t1.profile.review_service.security.details;

import com.t1.profile.review_service.dto.UserDetailsDto;
import com.t1.profile.review_service.exception.UserNotFoundException;
import com.t1.profile.review_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetailsDto user = userService.getByEmail(email);
        if (user == null) {
            throw new UserNotFoundException(email);
        }
        return new UserDetailsImpl(user);
    }

}
