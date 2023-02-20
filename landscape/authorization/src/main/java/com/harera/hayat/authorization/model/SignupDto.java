package com.harera.hayat.authorization.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
public class SignupDto implements Serializable {

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;
}

