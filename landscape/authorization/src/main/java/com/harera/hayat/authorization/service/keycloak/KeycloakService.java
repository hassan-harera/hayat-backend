package com.harera.hayat.authorization.service.keycloak;

import com.harera.hayat.authorization.model.auth.LoginResponse;
import com.harera.hayat.authorization.model.user.User;


public interface KeycloakService {

    LoginResponse login(String username, String password);

    void signup(User user, String password);

    void logout(String token, String refreshToken);

    void resetPassword(User user, String newPassword);

    void oauthSignup(User user);
}
