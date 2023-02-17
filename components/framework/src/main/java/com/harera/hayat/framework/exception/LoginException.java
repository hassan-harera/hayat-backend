package com.harera.hayat.framework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ResponseStatus(HttpStatus.FORBIDDEN)
public class LoginException extends RuntimeException {

    private String code;

    public LoginException(String errorCode, String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "LogicError {" + "code='" + code + '\'' + ", message='" + getMessage()
                        + '\'';
    }
}
