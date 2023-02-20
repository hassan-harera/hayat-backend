package com.harera.hayat.authorization.service;

import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.harera.hayat.authorization.model.auth.*;
import com.harera.hayat.authorization.model.oauth.OAuthLoginRequest;
import com.harera.hayat.authorization.model.oauth.OauthSignupRequest;
import com.harera.hayat.authorization.model.user.FirebaseUser;
import com.harera.hayat.authorization.model.auth.SignupRequest;
import com.harera.hayat.authorization.model.user.AuthUser;
import com.harera.hayat.authorization.repository.TokenRepository;
import com.harera.hayat.authorization.repository.UserRepository;
import com.harera.hayat.authorization.service.firebase.FirebaseServiceImpl;
import com.harera.hayat.authorization.service.keycloak.KeycloakService;
import com.harera.hayat.framework.exception.SignupException;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.harera.hayat.authorization.util.RegexUtils.*;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthValidation authValidation;
    private final TokenRepository tokenRepository;
    private final FirebaseServiceImpl firebaseServiceImpl;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;
    private final KeycloakService keycloakService;

    public AuthService(UserRepository userRepository, JwtService jwtService,
                       PasswordEncoder passwordEncoder, AuthValidation authValidation,
                       TokenRepository tokenRepository,
                       FirebaseServiceImpl firebaseServiceImpl, ModelMapper modelMapper,
                       JwtUtils jwtUtils, KeycloakService keycloakService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authValidation = authValidation;
        this.tokenRepository = tokenRepository;
        this.firebaseServiceImpl = firebaseServiceImpl;
        this.modelMapper = modelMapper;
        this.jwtUtils = jwtUtils;
        this.keycloakService = keycloakService;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        authValidation.validateLogin(loginRequest);

        long userId = getUserId(loginRequest.getSubject());
        AuthUser user = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));

        if (!Objects.equals(user.getDeviceToken(), loginRequest.getDeviceToken())) {
            user.setDeviceToken(loginRequest.getDeviceToken());
            userRepository.save(user);
        }

        return keycloakService.login(loginRequest);
    }

    public LoginResponse login(OAuthLoginRequest oAuthLoginRequest) {
        authValidation.validate(oAuthLoginRequest);
        FirebaseToken firebaseToken = firebaseServiceImpl
                .getFirebaseToken(oAuthLoginRequest.getFirebaseToken());
        UserRecord userRecord = firebaseServiceImpl.getUser(firebaseToken.getUid());
        AuthUser user = userRepository.findByUid(userRecord.getUid()).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
        return new LoginResponse(jwtService.generateToken(user),
                jwtService.generateRefreshToken(user));
    }

    public SignupResponse signup(OauthSignupRequest signupRequest) {
        authValidation.validate(signupRequest);
        FirebaseUser firebaseUser = firebaseServiceImpl.createUser(signupRequest);
        if (firebaseUser == null) {
            throw new SignupException("User creation failed");
        }

        AuthUser user = modelMapper.map(signupRequest, AuthUser.class);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setUid(firebaseUser.getUid());
        user.setUsername(firebaseUser.getUid());

        userRepository.save(user);
        return modelMapper.map(user, SignupResponse.class);
    }

    private long getUserId(String subject) {
        Optional<AuthUser> user = Optional.empty();
        if (isPhoneNumber(subject)) {
            user = userRepository.findByMobile(subject);
        } else if (isEmail(subject)) {
            user = userRepository.findByEmail(subject);
        } else if (isUsername(subject)) {
            user = userRepository.findByUsername(subject);
        }
        if (user.isPresent()) {
            return user.get().getId();
        }
        return 0;
    }

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        try {
            long userId = Integer.parseInt(username);
            return userRepository.findById(userId).orElse(null);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public LoginResponse refresh(String refreshToken) {
        String usernameOrMobile = jwtUtils.extractUserSubject(refreshToken);
        final AuthUser user = (AuthUser) loadUserByUsername(usernameOrMobile);
        jwtUtils.validateRefreshToken(user, refreshToken);
        LoginResponse authResponse = new LoginResponse();
        authResponse.setToken(jwtService.generateToken(user));
        authResponse.setRefreshToken(jwtService.generateRefreshToken(user));
        return authResponse;
    }

    public FirebaseOauthToken generateFirebaseToken(LoginRequest loginRequest) {
        authValidation.validateLogin(loginRequest);
        long userId = getUserId(loginRequest.getSubject());
        AuthUser user = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
        String s = firebaseServiceImpl.generateToken(user.getUid());
        return new FirebaseOauthToken(s);
    }

    public void logout(LogoutRequest logoutRequest) {
        String usernameOrMobile = jwtUtils.extractUserSubject(logoutRequest.getToken());
        final AuthUser user = (AuthUser) loadUserByUsername(usernameOrMobile);
        if (StringUtils.isNotEmpty(user.getDeviceToken())) {
            user.setDeviceToken(null);
            userRepository.save(user);
        }
        jwtUtils.validateToken(user, logoutRequest.getToken());
        tokenRepository.removeUserToken(logoutRequest.getToken());
        if (StringUtils.isNotEmpty(logoutRequest.getRefreshToken())) {
            jwtUtils.validateRefreshToken(user, logoutRequest.getRefreshToken());
            tokenRepository.removeUserRefreshToken(logoutRequest.getRefreshToken());
        }
    }

    public SignupResponse signup(SignupRequest signupRequest) {
        authValidation.validate(signupRequest);
        AuthUser user = modelMapper.map(signupRequest, AuthUser.class);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        keycloakService.signup(user, signupRequest.getPassword());
        return modelMapper.map(userRepository.save(user), SignupResponse.class);
    }
}
