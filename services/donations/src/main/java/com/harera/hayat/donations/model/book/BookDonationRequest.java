package com.harera.hayat.donations.model.book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = { "id", "active", "user", "city",
        "category", "status", "image_url", "donation_date", "donation_expiration_date" })
public class BookDonationRequest extends BookDonationDto {

}
