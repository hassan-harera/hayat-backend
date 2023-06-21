package com.harera.hayat.authorization.service.oauth;

import com.google.firebase.auth.FirebaseToken;
import com.harera.hayat.authorization.model.auth.LoginResponse;
import com.harera.hayat.authorization.model.auth.SignupResponse;
import com.harera.hayat.authorization.model.oauth.OauthLoginRequest;
import com.harera.hayat.authorization.model.oauth.OauthSignupRequest;
import com.harera.hayat.authorization.model.user.User;
import com.harera.hayat.authorization.repository.UserRepository;
import com.harera.hayat.authorization.service.AuthorizationNotificationsService;
import com.harera.hayat.authorization.service.firebase.OauthFirebaseService;
import com.harera.hayat.authorization.service.keycloak.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OauthValidation oauthValidation;
    private final OauthFirebaseService oauthFirebaseService;
    private final ModelMapper modelMapper;
    private final KeycloakService keycloakService;
    private final AuthorizationNotificationsService notificationsService;

    public LoginResponse login(OauthLoginRequest oAuthLoginRequest) {
        oauthValidation.validateLogin(oAuthLoginRequest);

        FirebaseToken firebaseToken = oauthFirebaseService
                        .getFirebaseToken(oAuthLoginRequest.getFirebaseToken());
        User user = userRepository.findByUid(firebaseToken.getUid()).orElseThrow(
                        () -> new UsernameNotFoundException("User not found"));

        notificationsService.notifyNewLoginDetected(user);
        return keycloakService.login(user.getUsername(), user.getPassword());
    }

    public SignupResponse signup(OauthSignupRequest signupRequest) {
        oauthValidation.validateSignup(signupRequest);

        FirebaseToken firebaseUser = oauthFirebaseService
                        .getFirebaseToken(signupRequest.getOauthToken());

        User user = modelMapper.map(signupRequest, User.class);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setUid(firebaseUser.getUid());
        user.setUsername(firebaseUser.getUid());

        userRepository.save(user);
        keycloakService.signup(user);

        return modelMapper.map(user, SignupResponse.class);
    }
}
