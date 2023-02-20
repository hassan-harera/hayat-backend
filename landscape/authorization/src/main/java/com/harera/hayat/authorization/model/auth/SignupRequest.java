package com.harera.hayat.authorization.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.authorization.model.SignupDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class SignupRequest extends SignupDto {

}
