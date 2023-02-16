package com.harera.hayat.authorization.service.keycloak;

import com.harera.hayat.authorization.model.auth.LoginRequest;
import com.harera.hayat.authorization.model.auth.LoginResponse;


public interface KeycloakService {

    LoginResponse login(LoginRequest request);
}
