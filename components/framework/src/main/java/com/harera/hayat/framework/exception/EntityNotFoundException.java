package com.harera.hayat.framework.exception;

import com.harera.hayat.framework.model.BaseEntity;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private String code;

    public EntityNotFoundException(String message) {
        super(message);
        this.code = null;
    }

    public EntityNotFoundException(String message, String code) {
        super(message);
        this.code = code;
    }

    public EntityNotFoundException(Class<? extends BaseEntity> clazz, long id) {
        super(String.format("[%s] with id [%s] is not found", clazz.getSimpleName(), id));
        this.code = null;
    }

    public EntityNotFoundException(Class<? extends BaseEntity> clazz, long id,
                    String code) {
        this(String.format(clazz.getSimpleName(), id));
        this.code = code;
    }
}
