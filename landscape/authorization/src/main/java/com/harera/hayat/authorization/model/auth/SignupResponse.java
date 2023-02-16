package com.harera.hayat.authorization.model.auth;

import lombok.Data;

@Data
public class SignupResponse {
    private long id;
    private String uid;
    private String username;
    private String mobile;
    private String email;
    private String firstName;
    private String lastName;
    private String deviceToken;
}
