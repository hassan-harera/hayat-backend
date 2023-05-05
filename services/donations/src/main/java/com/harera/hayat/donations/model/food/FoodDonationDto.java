package com.harera.hayat.donations.model.food;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.donations.model.DonationDto;
import com.harera.hayat.framework.model.food.FoodUnitDto;
import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
public class FoodDonationDto extends DonationDto {

    @JsonProperty(value = "food_unit")
    private FoodUnitDto foodUnit;

    @JsonProperty(value = "quantity")
    private Float quantity;

    @JsonProperty(value = "food_expiration_date")
    private LocalDate foodExpirationDate;

    @JsonProperty(value = "city_id")
    private Long cityId;

    @JsonProperty(value = "food_unit_id")
    private Long foodUnitId;

    @JsonProperty(value = "food_category_id")
    private Long foodCategoryId;
}
