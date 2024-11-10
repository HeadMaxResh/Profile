package com.t1.profile.skill.hard.exception;

public class HardSkillNotFoundException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "HardSkill not found with id: ";

    public HardSkillNotFoundException(Integer hardSkillId) {
        super(MESSAGE_TEMPLATE + hardSkillId);
    }

    public static String getMessage(Integer hardSkillId) {
        return MESSAGE_TEMPLATE + hardSkillId;
    }

}

