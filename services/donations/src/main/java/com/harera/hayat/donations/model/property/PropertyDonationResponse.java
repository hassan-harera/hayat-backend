package com.harera.hayat.donations.model.property;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(value = { "city_id" })
public class PropertyDonationResponse extends PropertyDonationDto {

    private Long cityId;
}
