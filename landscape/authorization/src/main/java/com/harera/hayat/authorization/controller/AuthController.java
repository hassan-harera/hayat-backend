package com.harera.hayat.authorization.controller;

import com.harera.hayat.authorization.model.auth.*;
import com.harera.hayat.authorization.model.user.UserResponse;
import com.harera.hayat.authorization.service.auth.AuthService;
import com.harera.hayat.authorization.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@Log4j2
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Auth API")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ok(authService.login(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(
                    @RequestBody SignupRequest signupRequest) {
        return ok(authService.signup(signupRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequest logoutRequest) {
        authService.logout(logoutRequest);
        return ok().build();
    }

    @PostMapping("/password-reset")
    public ResponseEntity<Void> resetPassword(
                    @RequestBody ResetPasswordRequest resetPasswordRequest) {
        authService.resetPassword(resetPasswordRequest);
        return ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(
                    @RequestBody RefreshTokenRequest refreshTokenRequest) {
        authService.refresh(refreshTokenRequest);
        return ok().build();
    }

    @GetMapping
    public ResponseEntity<UserResponse> get(
                    @RequestHeader("Authorization") String authorizationHeader) {
        return ok(userService
                        .getProfileWithAuthorization(authorizationHeader.substring(7)));
    }
}
