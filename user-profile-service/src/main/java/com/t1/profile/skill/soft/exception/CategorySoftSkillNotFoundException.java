package com.t1.profile.skill.soft.exception;

public class CategorySoftSkillNotFoundException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Category not found with id: ";

    public CategorySoftSkillNotFoundException(Integer categoryId) {
        super(MESSAGE_TEMPLATE + categoryId);
    }

    public static String getMessage(Integer categoryId) {
        return MESSAGE_TEMPLATE + categoryId;
    }

}
