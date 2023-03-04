package com.harera.hayat.donations.model.property;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true, value = { "id", "user", "city", "status",
        "category", "donation_date", "donation_expiration_date" })
public class PropertyDonationUpdateRequest extends PropertyDonationDto {

    private Long cityId;
}
