package com.harera.hayat.framework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FieldFormatException extends FieldException {

    public FieldFormatException(String code, String field, String value) {
        super(String.format("Invalid %s: %s Format", field, value), code, field);
    }

    public FieldFormatException(String code, String field) {
        super(code, field);
    }

    public FieldFormatException(String message, Throwable cause, String code,
                    String field) {
        super(message, cause, code, field);
    }

    public FieldFormatException(Throwable cause, String code, String field) {
        super(cause, code, field);
    }
}
