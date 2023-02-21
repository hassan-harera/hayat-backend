package com.harera.hayat.donations.model.medicine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true,
                value = { "id", "active", "city", "user", "unit", "medicine" })
public class MedicineDonationRequest extends MedicineDonationDto {

    @JsonProperty(value = "city_id")
    private Long cityId;

    @JsonProperty(value = "unit_id")
    private Long unitId;

    @JsonProperty(value = "medicine_id")
    private Long medicineId;

    @JsonProperty(value = "medicine_expiration_date")
    private OffsetDateTime medicineExpirationDate;
}
