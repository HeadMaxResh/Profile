package com.t1.profile.skill.soft.exception;

public class SoftSkillNotFoundException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "SoftSkill not found with id: ";

    public SoftSkillNotFoundException(Integer softSkillId) {
        super(MESSAGE_TEMPLATE + softSkillId);
    }

    public static String getMessage(Integer softSkillId) {
        return MESSAGE_TEMPLATE + softSkillId;
    }

}
