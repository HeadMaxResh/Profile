package com.t1.profile.user.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;

@Getter
public class EntityNotFoundByNameException extends EntityNotFoundException {

    private final Class<?> entityClass;
    private final String name;

    public EntityNotFoundByNameException(Class<?> tClass, String name) {
        super("Could not find " + tClass.getSimpleName() + " with name \"" + name + "\"");
        this.entityClass = tClass;
        this.name = name;
    }

}
