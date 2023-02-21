package com.harera.hayat.donations.model.food;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = { "id", "user", "city", "unit",
        "category", "donation_date", "donation_expiration_date" })
public class FoodDonationUpdateRequest extends FoodDonationDto {

}
