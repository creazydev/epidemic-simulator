package com.github.epidemicsimulator.adapter.in.web.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException{
    private final Long id;
    private final String entityName;

    public EntityNotFoundException(Long id, String entityName) {
        super(entityName + " could not be found with id: " + id);
        this.id = id;
        this.entityName = entityName;
    }
}
