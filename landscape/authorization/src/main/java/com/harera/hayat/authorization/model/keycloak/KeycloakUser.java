package com.harera.hayat.authorization.model.keycloak;

import lombok.Data;

@Data
public class KeycloakUser {

    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String username;
    private String password;
    private String role;
}
