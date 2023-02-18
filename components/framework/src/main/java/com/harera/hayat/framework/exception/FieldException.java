package com.harera.hayat.framework.exception;

import lombok.Getter;

@Getter
public class FieldException extends RuntimeException {

    protected final String code;
    protected final String field;

    public FieldException(String code, String field) {
        this.code = code;
        this.field = field;
    }

    public FieldException(String message, String code, String field) {
        super(message);
        this.code = code;
        this.field = field;
    }

    public FieldException(String message, Throwable cause, String code, String field) {
        super(message, cause);
        this.code = code;
        this.field = field;
    }

    public FieldException(Throwable cause, String code, String field) {
        super(cause);
        this.code = code;
        this.field = field;
    }

}
