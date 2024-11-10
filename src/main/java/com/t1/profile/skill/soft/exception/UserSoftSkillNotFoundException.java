package com.t1.profile.skill.soft.exception;

public class UserSoftSkillNotFoundException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "UserSoftSkill not found с id: ";

    public UserSoftSkillNotFoundException(Integer userSoftSkillId) {
        super(MESSAGE_TEMPLATE + userSoftSkillId);
    }

    public static String getMessage(Integer userSoftSkillId) {
        return MESSAGE_TEMPLATE + userSoftSkillId;
    }


}
