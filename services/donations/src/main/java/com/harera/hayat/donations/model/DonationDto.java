package com.harera.hayat.donations.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.framework.model.BaseEntityDto;
import com.harera.hayat.framework.model.city.CityDto;
import com.harera.hayat.framework.model.user.BaseUserDto;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
public class DonationDto extends BaseEntityDto {

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "donation_date")
    private OffsetDateTime donationDate;

    @JsonProperty(value = "donation_expiration_date")
    private OffsetDateTime donationExpirationDate;

    @JsonProperty(value = "category")
    private DonationCategory category;

    @JsonProperty(value = "status")
    private DonationStatus status;

    @JsonProperty("communication_method")
    private CommunicationMethod communicationMethod;

    @JsonProperty("city_id")
    private Long cityId;

    @JsonProperty(value = "city")
    private CityDto city;

    @JsonProperty(value = "user")
    private BaseUserDto user;

    @JsonProperty(value = "image_url")
    private String imageUrl;
}
