package com.harera.hayat.donations.model.clothing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.donations.model.CommunicationMethod;
import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.DonationStatus;
import com.harera.hayat.framework.model.BaseEntityDto;
import com.harera.hayat.framework.model.city.CityDto;
import com.harera.hayat.framework.model.clothing.ClothingCondition;
import com.harera.hayat.framework.model.clothing.ClothingSeason;
import com.harera.hayat.framework.model.clothing.ClothingSize;
import com.harera.hayat.framework.model.clothing.ClothingType;
import com.harera.hayat.framework.model.user.BaseUserDto;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ClothingDonationResponse extends BaseEntityDto {

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

    @JsonProperty(value = "city")
    private CityDto city;

    @JsonProperty(value = "user")
    private BaseUserDto user;

    @JsonProperty(value = "image_url")
    private String imageUrl;

    @JsonProperty(value = "telegram_link")
    private String telegramLink;

    @JsonProperty(value = "whatsapp_link")
    private String whatsappLink;

    @JsonProperty(value = "qr_code")
    private String qrCode;

    @JsonProperty(value = "reputation")
    private Integer reputation;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("clothing_season")
    private ClothingSeason clothingSeason;

    @JsonProperty("clothing_condition")
    private ClothingCondition clothingCondition;

    @JsonProperty("clothing_size")
    private ClothingSize clothingSize;

    @JsonProperty("clothing_category")
    private ClothingType clothingType;

    @JsonProperty("clothing_type")
    private ClothingType type;
}
