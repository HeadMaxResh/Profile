package com.t1.profile.user.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.User;

public class UserNotFoundByIdAndProfessionException extends EntityNotFoundException {

    private final Long id;

    public UserNotFoundByIdAndProfessionException(Long id) {
        super("Could not find " + User.class.getSimpleName() + " with id \"" + id + "\" and profession");
        this.id = id;
    }

}
