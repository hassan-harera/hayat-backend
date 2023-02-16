package com.harera.hayat.authorization.model.auth;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserAuthorityId implements Serializable {

    private String authority;
    private Long userId;
}
