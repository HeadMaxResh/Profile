package com.t1.profile.user.exception;

import jakarta.persistence.EntityNotFoundException;

public class UserNotFoundByEmailException extends EntityNotFoundException {

    public UserNotFoundByEmailException(String email) {
        super(String.format("User with email %s not found", email));
    }
}
