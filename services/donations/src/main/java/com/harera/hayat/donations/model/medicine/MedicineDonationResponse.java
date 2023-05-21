package com.harera.hayat.donations.model.medicine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = { "active", "city_id", "user_id",
        "medicine_unit_id", "medicine_id" })
public class MedicineDonationResponse extends MedicineDonationDto {

}
