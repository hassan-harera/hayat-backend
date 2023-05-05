package com.harera.hayat.authorization.model.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginRequest {

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("password")
    private String password;

    @JsonProperty("device_token")
    private String deviceToken;
}
