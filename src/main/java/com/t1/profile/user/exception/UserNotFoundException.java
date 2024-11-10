package com.t1.profile.user.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE_ID = "User not found with id: ";
    private static final String MESSAGE_TEMPLATE_EMAIL = "User not found with email: ";

    public UserNotFoundException(Integer userId) {
        super(MESSAGE_TEMPLATE_ID + userId);
    }

    public UserNotFoundException(String email) {
        super(MESSAGE_TEMPLATE_EMAIL + email);
    }

    public static String getMessage(Integer userId) {
        return MESSAGE_TEMPLATE_ID + userId;
    }

    public static String getMessage(String email) {
        return MESSAGE_TEMPLATE_EMAIL + email;
    }

}
