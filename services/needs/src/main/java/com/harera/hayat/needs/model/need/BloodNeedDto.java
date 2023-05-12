package com.harera.hayat.needs.model.need;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.needs.model.Need;
import com.harera.hayat.needs.model.NeedDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
