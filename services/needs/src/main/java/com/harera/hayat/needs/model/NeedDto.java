package com.harera.hayat.needs.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.framework.model.BaseEntityDto;
import com.harera.hayat.framework.model.city.CityDto;
import com.harera.hayat.framework.model.user.UserDto;

import lombok.Data;

@Data
public class NeedDto extends BaseEntityDto {

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "need_date")
    private LocalDateTime needDate;

    @JsonProperty(value = "need_expiration_date")
    private LocalDateTime needExpirationDate;

    @JsonProperty(value = "category")
    private NeedCategory category;

    @JsonProperty(value = "status")
    private NeedState status;

    @JsonProperty("communication_method")
    private CommunicationMethod communicationMethod;

    @JsonProperty("city")
    private CityDto city;

    @JsonProperty("city_id")
    private Long cityId;

    @JsonProperty("user")
    private UserDto user;
}
