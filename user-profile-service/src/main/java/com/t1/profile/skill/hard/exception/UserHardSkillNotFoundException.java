package com.t1.profile.skill.hard.exception;

public class UserHardSkillNotFoundException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "User hard skill not found with id: ";

    public UserHardSkillNotFoundException(Integer userHardSkillId) {
        super(MESSAGE_TEMPLATE + userHardSkillId);
    }

    public static String getMessage(Integer userHardSkillId) {
        return MESSAGE_TEMPLATE + userHardSkillId;
    }

}
