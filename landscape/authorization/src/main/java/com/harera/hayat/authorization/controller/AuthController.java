package com.harera.hayat.authorization.controller;

import static org.springframework.http.ResponseEntity.ok;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.authorization.model.auth.FirebaseOauthToken;
import com.harera.hayat.authorization.model.auth.LoginRequest;
import com.harera.hayat.authorization.model.auth.LoginResponse;
import com.harera.hayat.authorization.model.auth.LogoutRequest;
import com.harera.hayat.authorization.model.auth.OAuthLoginRequest;
import com.harera.hayat.authorization.model.auth.SignupRequest;
import com.harera.hayat.authorization.model.auth.SignupResponse;
import com.harera.hayat.authorization.service.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Auth", description = "Auth API")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ok(authService.login(loginRequest));
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<SignupResponse> signup(SignupRequest signupRequest) {
        return ok(authService.signup(signupRequest));
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequest logoutRequest) {
        authService.logout(logoutRequest);
        return ok().build();
    }

    @PostMapping("/oauth/login")
    public ResponseEntity<LoginResponse> login(
                    @RequestBody OAuthLoginRequest loginRequest) {
        return ok(authService.login(loginRequest));
    }

    @PostMapping("/oauth/firebase-tokens")
    public ResponseEntity<FirebaseOauthToken> generateFirebaseToken(
                    @RequestBody LoginRequest loginRequest) {
        return ok(authService.generateFirebaseToken(loginRequest));
    }
}
