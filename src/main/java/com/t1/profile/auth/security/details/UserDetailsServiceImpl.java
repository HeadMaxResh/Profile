package com.t1.profile.auth.security.details;

import com.t1.profile.user.exception.UserNotFoundByEmailException;
import com.t1.profile.user.model.User;
import com.t1.profile.user.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundByEmailException(email);
        }
        return new UserDetailsImpl(user);
    }

}
