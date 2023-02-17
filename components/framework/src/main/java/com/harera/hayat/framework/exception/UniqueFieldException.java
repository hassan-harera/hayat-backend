package com.harera.hayat.framework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UniqueFieldException extends FieldException {

    public UniqueFieldException(String code, String field, String value) {
        super(String.format("%s: %s is already exists", field, value), code, field);
    }
}
