package com.harera.hayat.donations.service;

import com.harera.hayat.framework.exception.InvalidTokenException;
import com.harera.hayat.framework.model.user.BaseUser;
import com.harera.hayat.framework.repository.user.BaseUserRepository;
import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

@Log4j2
public abstract class BaseService {

    @Autowired
    private BaseUserRepository baseUserRepository = null;

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
