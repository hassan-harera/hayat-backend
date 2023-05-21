package com.harera.hayat.donations.model.clothing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.donations.model.DonationDto;
import com.harera.hayat.framework.model.clothing.ClothingCondition;
import com.harera.hayat.framework.model.clothing.ClothingType;
import com.harera.hayat.framework.model.clothing.ClothingSeason;
import com.harera.hayat.framework.model.clothing.ClothingSize;
import lombok.Data;

@Data
public class ClothingDonationDto extends DonationDto {

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("clothing_season")
    private ClothingSeason clothingSeason;

    @JsonProperty("clothing_condition")
    private ClothingCondition clothingCondition;

    @JsonProperty("clothing_size")
    private ClothingSize clothingSize;

    @JsonProperty("clothing_category")
    private ClothingType clothingType;

    @JsonProperty("clothing_type")
    private ClothingType type;

    @JsonProperty("clothing_condition_id")
    private Long clothingConditionId;
}
