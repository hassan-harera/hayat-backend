package com.harera.hayat.authorization.service.user;

import com.harera.hayat.authorization.model.user.User;
import com.harera.hayat.authorization.model.user.UserResponse;
import com.harera.hayat.authorization.repository.UserRepository;
import com.harera.hayat.authorization.service.UserUtils;
import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.exception.InvalidTokenException;
import lombok.extern.log4j.Log4j2;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserUtils userUtils;
    public UserService(UserRepository userRepository, ModelMapper modelMapper,
                    UserUtils userUtils) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userUtils = userUtils;
    }

    public UserResponse get(long id) {
        return userRepository.findById(id)
                        .map(user -> modelMapper.map(user, UserResponse.class))
                        .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public UserResponse getProfileWithAuthorization(String token) {
        User user = getUser(token);
        return modelMapper.map(user, UserResponse.class);
    }

    private User getUser(String authorization) {
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
