package com.t1.profile.profession.exception;

public class UserProfessionNotFoundException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Profession not found for user with id: ";

    public UserProfessionNotFoundException(Integer userId) {
        super(MESSAGE_TEMPLATE + userId);
    }

    public static String getMessage(Integer userId) {
        return MESSAGE_TEMPLATE + userId;
    }

}
