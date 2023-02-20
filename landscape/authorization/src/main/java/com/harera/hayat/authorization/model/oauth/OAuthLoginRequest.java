package com.harera.hayat.authorization.model.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OAuthLoginRequest {

    @JsonProperty("firebase_token")
    private String firebaseToken;

    @JsonProperty("device_token")
    private String deviceToken;
}
