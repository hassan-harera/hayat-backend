package com.harera.hayat.shared.controller;

import com.harera.hayat.framework.model.user.BaseUser;
import com.harera.hayat.shared.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@Log4j2
@RestController
@RequestMapping("/api/v1")
@Tag(name = "User", description = "User API")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<BaseUser> get(
                    @RequestHeader("Authorization") String authorizationHeader) {
        return ok(userService
                        .getProfile(authorizationHeader.substring(7)));
    }
}
