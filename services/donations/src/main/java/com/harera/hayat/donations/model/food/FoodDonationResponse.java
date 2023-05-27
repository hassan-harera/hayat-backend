package com.harera.hayat.donations.model.food;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties({ "food_unit_id", "food_category_id", "city_id" })
public class FoodDonationResponse extends FoodDonationDto {
}
