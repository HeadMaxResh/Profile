package com.t1.profile.user.exception;

public class RatedUserNotFoundException extends UserNotFoundException {

    private static final String MESSAGE_TEMPLATE = "Rated user not found with id: ";

    public RatedUserNotFoundException(Integer userId) {
        super(MESSAGE_TEMPLATE + userId);
    }

    public static String getMessage(Integer userId) {
        return MESSAGE_TEMPLATE + userId;
    }

}
