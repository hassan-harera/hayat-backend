package com.harera.hayat.framework.model.food;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.framework.model.BaseEntityDto;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class FoodCategoryDto extends BaseEntityDto {

    @JsonProperty("arabic_name")
    private String arabicName;

    @JsonProperty("english_name")
    private String englishName;

    @Column(name = "description")
    private String description;
}
