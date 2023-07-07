package com.harera.hayat.shared.service;

import com.harera.hayat.framework.exception.InvalidTokenException;
import com.harera.hayat.framework.model.user.BaseUser;
import com.harera.hayat.framework.repository.user.BaseUserRepository;
import lombok.extern.log4j.Log4j2;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserService {

    private final BaseUserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(BaseUserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public BaseUser getProfile(String token) {
        BaseUser user = getUser(token);
        return modelMapper.map(user, BaseUser.class);
    }

    private BaseUser getUser(String authorization) {
        try {
            AccessToken token = TokenVerifier.create(authorization, AccessToken.class)
                            .getToken();
            String mobile = token.getPreferredUsername();
            return userRepository.findByMobile(mobile).orElseThrow(
                            () -> new InvalidTokenException("", "Invalid token"));
        } catch (VerificationException e) {
            log.error(e);
            return null;
        }
    }
}
