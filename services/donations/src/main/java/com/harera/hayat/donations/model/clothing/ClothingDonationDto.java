package com.harera.hayat.donations.model.clothing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.donations.model.DonationDto;
import com.harera.hayat.framework.model.ClothingCondition;
import com.harera.hayat.framework.model.ClothingType;
import com.harera.hayat.framework.model.ClothingSeason;
import com.harera.hayat.framework.model.ClothingSize;
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

    @JsonProperty("clothing_season")
    private ClothingSeason.Season season;

    @JsonProperty("clothing_condition")
    private ClothingCondition.Condition condition;

    @JsonProperty("clothing_size")
    private ClothingSize.Size size;

    @JsonProperty("clothing_type")
    private ClothingType.Type type;
}
