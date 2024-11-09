package com.t1.profile.profession.exception;

public class ProfessionNotFoundException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Profession not found with id: ";

    public ProfessionNotFoundException(Integer professionId) {
        super(MESSAGE_TEMPLATE + professionId);
    }

    public static String getMessage(Integer professionId) {
        return MESSAGE_TEMPLATE + professionId;
    }

}
