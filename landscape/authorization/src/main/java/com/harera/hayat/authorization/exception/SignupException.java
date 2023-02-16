package com.harera.hayat.authorization.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
public class SignupException extends RuntimeException {

    public SignupException(String message) {
        super(message);
    }
}
