package com.harera.hayat.authorization.service.keycloak;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harera.hayat.authorization.model.auth.LoginRequest;
import com.harera.hayat.authorization.model.auth.LoginResponse;
import com.harera.hayat.authorization.model.user.AuthUser;
import com.harera.hayat.framework.exception.LoginException;
import com.harera.hayat.framework.exception.SignupException;
import com.harera.hayat.framework.util.ErrorCode;

import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
@Transactional
public class KeycloakServiceImpl implements KeycloakService {

    private final Keycloak keycloak;
    private final ModelMapper modelMapper;
    private final String clientId;
    private final String serverUrl;
    private final String realm;
    private final String clientSecret;

    public KeycloakServiceImpl(Keycloak keycloak, ModelMapper modelMapper,
                               @Value("${keycloak.auth-server-url}") String serverUrl,
                               @Value("${keycloak.realm}") String realm,
                               @Value("${keycloak.credentials.client-id}") String clientId,
                               @Value("${keycloak.credentials.secret}") String clientSecret,
                               @Value("${keycloak.credentials.username}") String username,
                               @Value("${keycloak.credentials.password}") String password) {
        this.keycloak = keycloak;
        this.modelMapper = modelMapper;
        this.clientId = clientId;
        this.serverUrl = serverUrl;
        this.realm = realm;
        this.clientSecret = clientSecret;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Keycloak loginKeycloak = buildLoginKeycloak(request.getSubject(), request.getPassword());
        try {
            return modelMapper.map(loginKeycloak.tokenManager().getAccessToken(), LoginResponse.class);
        } catch (Exception e) {
            log.error(e);
            throw new LoginException(ErrorCode.INVALID_LOGIN_CREDENTIALS, "Invalid login credentials");
        }
    }

    private Keycloak buildLoginKeycloak(String username, String password) {
        return KeycloakBuilder.builder()
                .realm(realm)
                .serverUrl(serverUrl)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(username)
                .password(password)
                .build();
    }

    @Override
    public void signup(AuthUser user, String rawPassword) {
        UserRepresentation userRepresentation = modelMapper.map(user, UserRepresentation.class);
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);
        userRepresentation.setAttributes(new HashMap<>());

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType("password");
        credentialRepresentation.setValue(rawPassword);
        credentialRepresentation.setTemporary(false);

        HashMap<String, List<String>> clientRoles = new HashMap<>();
        clientRoles.put(clientId, Collections.singletonList("user"));
        userRepresentation.setClientRoles(clientRoles);

        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
        try {
            keycloak.realm(realm).users().create(userRepresentation);
        } catch (Exception ex) {
            throw new SignupException("Error while processing creating");
        }
    }
}
