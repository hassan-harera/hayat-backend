package com.harera.hayat.donations.model.clothing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = { "id", "user", "city", "unit",
        "category", "donation_date", "donation_expiration_date" })
public class ClothingDonationUpdateRequest extends ClothingDonationDto {

}
