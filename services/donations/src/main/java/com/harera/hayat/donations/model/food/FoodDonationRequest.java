package com.harera.hayat.donations.model.food;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true,
                value = { "id", "active", "user", "city", "unit", "category" })
public class FoodDonationRequest extends FoodDonationDto {


}
