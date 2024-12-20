package com.t1.profile.common.web.security.exception.jwt;

public class JwtTokenMalformedException  extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Invalid JWT token: ";

    public JwtTokenMalformedException(String message) {
        super(MESSAGE_TEMPLATE + message);
    }

    public static String getMessage(String message) {
        return MESSAGE_TEMPLATE + message;
    }

}
