package com.harera.hayat.donations.model.medicine;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.donations.model.DonationDto;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class MedicineDonationDto extends DonationDto {

    private Float amount;
    private MedicineUnitDto unit;
    @JsonProperty("medicine_expiration_date")
    private OffsetDateTime medicineExpirationDate;
    private MedicineDto medicine;
}
