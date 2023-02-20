package com.harera.hayat.authorization.service.keycloak;

import com.harera.hayat.authorization.model.auth.LoginRequest;
import com.harera.hayat.authorization.model.auth.LoginResponse;
import com.harera.hayat.authorization.model.user.AuthUser;


public interface KeycloakService {

    LoginResponse login(LoginRequest request);

    void signup(AuthUser user, String rawPassword);
}
