package com.harera.hayat.authorization.controller;

import com.harera.hayat.authorization.model.auth.*;
import com.harera.hayat.authorization.model.oauth.OAuthLoginRequest;
import com.harera.hayat.authorization.model.oauth.OauthSignupRequest;
import com.harera.hayat.authorization.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@Log4j2
@RestController
@RequestMapping("/api/v1/oauth")
@Tag(name = "Oauth", description = "Oauth API")
public class OauthController {

    private final AuthService authService;

    public OauthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
                    @RequestBody OAuthLoginRequest loginRequest) {
        return ok(authService.login(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> oauthSignup(
            @RequestBody OauthSignupRequest oauthSignupRequest) {
        return ok(authService.signup(oauthSignupRequest));
    }

    @PostMapping("/firebase-tokens")
    public ResponseEntity<FirebaseOauthToken> generateFirebaseToken(
                    @RequestBody LoginRequest loginRequest) {
        return ok(authService.generateFirebaseToken(loginRequest));
    }
}
