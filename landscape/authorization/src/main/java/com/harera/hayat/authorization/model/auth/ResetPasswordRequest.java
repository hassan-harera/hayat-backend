package com.harera.hayat.authorization.model.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResetPasswordRequest {

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("otp")
    private String otp;

    @JsonProperty("new_password")
    private String newPassword;
}
