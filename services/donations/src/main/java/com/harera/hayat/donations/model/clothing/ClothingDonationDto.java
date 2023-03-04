package com.harera.hayat.donations.model.clothing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.donations.model.DonationDto;
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
    private ClothingGender clothingGender;

    @JsonProperty("clothing_season_id")
    private Long clothingSeasonId;

    @JsonProperty("clothing_condition_id")
    private Long clothingConditionId;

    @JsonProperty("clothing_size_id")
    private Long clothingSizeId;

    @JsonProperty("clothing_category_id")
    private Long clothingGenderId;
}
