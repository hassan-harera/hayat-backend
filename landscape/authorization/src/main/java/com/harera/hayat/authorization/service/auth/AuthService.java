package com.harera.hayat.authorization.service.auth;

import com.harera.hayat.authorization.model.auth.*;
import com.harera.hayat.authorization.model.user.User;
import com.harera.hayat.authorization.repository.UserRepository;
import com.harera.hayat.authorization.service.UserUtils;
import com.harera.hayat.authorization.service.keycloak.KeycloakService;
import com.harera.hayat.framework.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthValidation authValidation;
    private final ModelMapper modelMapper;
    private final KeycloakService keycloakService;
    private final UserUtils userUtils;

    public LoginResponse login(LoginRequest loginRequest) {
        authValidation.validateLogin(loginRequest);

        long userId = userUtils.getUserId(loginRequest.getSubject());
        User user = userRepository.findById(userId).orElseThrow(
                        () -> new UsernameNotFoundException("User not found"));

        if (isNotEmpty(loginRequest.getDeviceToken())) {
            user.setDeviceToken(loginRequest.getDeviceToken());
            userRepository.save(user);
        }

        return keycloakService.login(user.getUsername(), loginRequest.getPassword());
    }

    public SignupResponse signup(SignupRequest signupRequest) {
        authValidation.validateSignup(signupRequest);

        User user = modelMapper.map(signupRequest, User.class);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setUsername(user.getMobile());

        userRepository.save(user);
        keycloakService.signup(user);

        return modelMapper.map(user, SignupResponse.class);
    }

    public void logout(LogoutRequest logoutRequest) {
        keycloakService.logout(logoutRequest.getToken(), logoutRequest.getRefreshToken());
    }

    public LoginResponse refresh(RefreshTokenRequest refreshTokenRequest) {
        LoginResponse authResponse = new LoginResponse();
        // TODO
        return authResponse;
    }

    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        authValidation.validateResetPassword(resetPasswordRequest);

        long userId = userUtils.getUserId(resetPasswordRequest.getMobile());
        User user = userRepository.findById(userId).orElseThrow(
                        () -> new EntityNotFoundException(User.class, userId));

        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));

        keycloakService.resetPassword(user, resetPasswordRequest.getNewPassword());
        userRepository.save(user);
    }
}
