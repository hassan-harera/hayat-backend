package com.harera.hayat.authorization.model.auth;

import com.harera.hayat.authorization.model.SignupDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequest extends SignupDto {

    private String otp;
}
