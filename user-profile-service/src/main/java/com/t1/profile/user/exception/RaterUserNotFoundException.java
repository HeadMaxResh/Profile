package com.t1.profile.user.exception;

public class RaterUserNotFoundException extends UserNotFoundException {

    private static final String MESSAGE_TEMPLATE = "Rater user not found with id: ";

    public RaterUserNotFoundException(Integer userId) {
        super(MESSAGE_TEMPLATE + userId);
    }

    public static String getMessage(Integer userId) {
        return MESSAGE_TEMPLATE + userId;
    }

}
