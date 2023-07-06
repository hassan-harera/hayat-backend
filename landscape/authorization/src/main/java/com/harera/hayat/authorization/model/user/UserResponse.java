package com.harera.hayat.authorization.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.framework.model.BaseEntityDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse extends BaseEntityDto {

    @JsonProperty("uid")
    private String uid;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("phone_number")
    private String mobile;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("username")
    private String username;
}
