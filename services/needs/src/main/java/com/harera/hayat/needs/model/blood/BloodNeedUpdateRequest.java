package com.harera.hayat.needs.model.blood;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.framework.model.city.CityDto;
import com.harera.hayat.framework.model.user.BaseUserDto;
import com.harera.hayat.needs.model.CommunicationMethod;
import com.harera.hayat.needs.model.NeedCategory;
import com.harera.hayat.needs.model.NeedStatus;
import lombok.Data;

@Data
public class BloodNeedUpdateRequest {

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("age")
    private int age;

    @JsonProperty("blood_type")
    private String bloodType;

    @JsonProperty("hospital")
    private String hospital;

    @JsonProperty("illness")
    private String illness;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty("communication_method")
    private CommunicationMethod communicationMethod;

    @JsonProperty("city_id")
    private Long cityId;
}
