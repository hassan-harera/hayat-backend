package com.harera.hayat.needs.service;

import org.springframework.security.core.context.SecurityContextHolder;

import com.harera.hayat.framework.model.user.User;

import io.jsonwebtoken.JwtException;

public interface BaseService {

    default User getRequestUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (principal instanceof User user)
            return user;
        throw new JwtException("Invalid token");
    }
}
