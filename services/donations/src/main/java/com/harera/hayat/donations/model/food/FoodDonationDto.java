package com.harera.hayat.donations.model.food;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.donations.model.DonationDto;
import com.harera.hayat.framework.model.food.FoodUnitDto;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class FoodDonationDto extends DonationDto {

    private FoodUnitDto unit;

    private Float amount;

    @JsonProperty(value = "food_expiration_date")
    private OffsetDateTime foodExpirationDate;

    @JsonProperty(value = "city_id")
    private Long cityId;

    @JsonProperty(value = "unit_id")
    private Long unitId;

    @JsonProperty(value = "food_category_id")
    private Long foodCategoryId;
}
