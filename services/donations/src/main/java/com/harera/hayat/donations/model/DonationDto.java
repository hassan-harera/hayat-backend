package com.harera.hayat.donations.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.donations.model.image.DonationImageDto;
import com.harera.hayat.framework.model.BaseEntityDto;
import com.harera.hayat.framework.model.city.CityDto;
import com.harera.hayat.framework.model.user.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Setter
@Getter
public class DonationDto extends BaseEntityDto {

    private String title;

    private String description;

    @JsonProperty(value = "donation_date")
    private OffsetDateTime donationDate;

    @JsonProperty(value = "donation_expiration_date")
    private OffsetDateTime donationExpirationDate;

    private DonationCategory category;

    private DonationState state = DonationState.PENDING;

    @JsonProperty("communication_method")
    private CommunicationMethod communicationMethod;

    @JsonProperty("city_id")
    private Long cityId;

    private CityDto city;

    private UserDto user;

    private List<DonationImageDto> images;
}
