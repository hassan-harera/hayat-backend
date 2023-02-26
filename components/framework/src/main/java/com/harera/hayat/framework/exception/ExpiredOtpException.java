package com.harera.hayat.framework.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.harera.hayat.framework.util.ErrorCode;
import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = BAD_REQUEST)
public class ExpiredOtpException extends RuntimeException {

    private final String code;

    public ExpiredOtpException(
            String mobile,
            String otp
    ) {
        super(String.format("otp %s is expired", otp, mobile));
        code = ErrorCode.EXPIRED_OTP;
    }
}
