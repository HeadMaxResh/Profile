package com.t1.profile.skill.hard.exception;

public class UserHardSkillAssociationNotFoundException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Hard skill not associated with user with id: ";

    public UserHardSkillAssociationNotFoundException(Integer userId) {
        super(MESSAGE_TEMPLATE + userId);
    }

    public static String getMessage(Integer userId) {
        return MESSAGE_TEMPLATE + userId;
    }

}
