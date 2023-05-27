package com.harera.hayat.donations.model.medicine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties({ "food_unit_id", "food_category_id", "city_id", "active",
        "user_id", "medicine_unit_id", "medicine_id" })
public class MedicineDonationResponse extends MedicineDonationDto {

}
