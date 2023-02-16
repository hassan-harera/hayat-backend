package com.harera.hayat.authorization.service.keycloak;


import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harera.hayat.authorization.exception.LoginException;
import com.harera.hayat.authorization.model.auth.LoginRequest;
import com.harera.hayat.authorization.model.auth.LoginResponse;
import com.harera.hayat.authorization.util.ErrorCode;


@Service
@Transactional
public class KeycloakServiceImpl implements KeycloakService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final Keycloak keycloak;
    private final ModelMapper modelMapper;
    private final String clientId;
    private final String serverUrl;
    private final String realm;
    private final String clientSecret;
    private final String username;
    private final String password;

    public KeycloakServiceImpl(Keycloak keycloak, ModelMapper modelMapper,
                               @Value("${keycloak.auth-server-url}") String serverUrl,
                               @Value("${keycloak.realm}") String realm,
                               @Value("${keycloak.credentials.client-id}") String clientId,
                               @Value("${keycloak.credentials.secret}") String clientSecret,
                               @Value("${keycloak.credentials.username}") String username,
                               @Value("${keycloak.credentials.password}") String password
    ) {
        this.keycloak = keycloak;
        this.modelMapper = modelMapper;
        this.clientId = clientId;
        this.serverUrl = serverUrl;
        this.realm = realm;
        this.clientSecret = clientSecret;
        this.username = username;
        this.password = password;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Keycloak loginKeycloak = buildKeycloak(request.getSubject(), request.getPassword());
        AccessTokenResponse accessTokenResponse = null;
        try {
            return modelMapper.map(loginKeycloak.tokenManager().getAccessToken(), LoginResponse.class);
        } catch (Exception e) {
            throw new LoginException(ErrorCode.INVALID_LOGIN_CREDENTIALS, "Invalid login credentials");
        }
    }

    private Keycloak buildKeycloak(String username, String password) {
        return KeycloakBuilder.builder()
                .realm(realm)
                .serverUrl(serverUrl)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(username)
                .password(password)
                .build();
    }
}
