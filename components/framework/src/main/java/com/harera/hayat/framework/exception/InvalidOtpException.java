package com.harera.hayat.framework.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.harera.hayat.framework.util.ErrorCode;
import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = BAD_REQUEST)
public class InvalidOtpException extends RuntimeException {

    private final String code;

    public InvalidOtpException(
            String mobile,
            String otp
    ) {
        super(String.format("Invalid otp %s for mobile %s", otp, mobile));
        code = ErrorCode.INVALID_OTP;
    }
}
