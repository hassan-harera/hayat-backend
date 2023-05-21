package com.harera.hayat.needs.service;

import org.springframework.security.core.context.SecurityContextHolder;

import com.harera.hayat.framework.model.user.BaseUser;

import io.jsonwebtoken.JwtException;

public interface BaseService {

    default BaseUser getRequestUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (principal instanceof BaseUser user)
            return user;
        throw new JwtException("Invalid token");
    }
}
