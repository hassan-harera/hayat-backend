package com.harera.hayat.needs.service;

import com.harera.hayat.framework.exception.InvalidTokenException;
import com.harera.hayat.framework.model.user.BaseUser;
import com.harera.hayat.framework.repository.user.BaseUserRepository;
import lombok.extern.log4j.Log4j2;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserService {

    private final BaseUserRepository baseUserRepository;

    public UserService(BaseUserRepository baseUserRepository) {
        this.baseUserRepository = baseUserRepository;
    }

    public BaseUser getUser(String authorization) {
        try {
            AccessToken token = TokenVerifier.create(authorization, AccessToken.class)
                            .getToken();
            String mobile = token.getPreferredUsername();
            return baseUserRepository.findByMobile(mobile).orElseThrow(
                            () -> new InvalidTokenException("", "Invalid token"));
        } catch (VerificationException e) {
            log.error(e);
            return null;
        }
    }
}
