package com.harera.hayat.framework.model.city;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = { "active" })
public class CityResponse extends CityDto {

    @JsonProperty("state")
    private StateResponse state;
}
