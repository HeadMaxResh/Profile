package com.t1.profile.skill.hard.exception;

public class UserHardSkillAssociationAlreadyExistException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Hard skill already associated with user with id: ";

    public UserHardSkillAssociationAlreadyExistException(Integer userId) {
        super(MESSAGE_TEMPLATE + userId);
    }

    public static String getMessage(Integer userId) {
        return MESSAGE_TEMPLATE + userId;
    }

}
