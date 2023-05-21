package com.harera.hayat.needs.model.blood;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.needs.model.NeedDto;
import lombok.Data;

@Data
public class BloodNeedDto extends NeedDto {

    @JsonProperty("age")
    private int age;

    @JsonProperty("blood_type")
    private String bloodType;

    @JsonProperty("hospital")
    private String hospital;

    @JsonProperty("illness")
    private String illness;

    //TODO: add urgency with api
    //TODO: make illness api
}
