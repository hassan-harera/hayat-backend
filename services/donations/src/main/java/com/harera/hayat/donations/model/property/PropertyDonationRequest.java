package com.harera.hayat.donations.model.property;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true,
                value = { "id", "active", "user", "city", "date", "donation_expiration_date" })
public class PropertyDonationRequest extends PropertyDonationDto {

    private Long cityId;
}
