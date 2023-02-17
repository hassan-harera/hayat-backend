package com.harera.hayat.framework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;

@Data
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LogicError extends RuntimeException {
    private String code;

    public LogicError(String message, String code) {
        super(message);
        this.code = code;
    }

    @Override
    public String toString() {
        return "LogicError{" + "code='" + code + '\'' + ", message='" + getMessage()
                        + '}';
    }
}
