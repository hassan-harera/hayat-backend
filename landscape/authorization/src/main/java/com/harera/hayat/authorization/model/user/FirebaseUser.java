package com.harera.hayat.authorization.model.user;

import lombok.Data;

@Data
public class FirebaseUser extends UserDto {

    private String uid;
    private String email;
    private String phoneNumber;
    private String displayName;
    private String photoUrl;
}
