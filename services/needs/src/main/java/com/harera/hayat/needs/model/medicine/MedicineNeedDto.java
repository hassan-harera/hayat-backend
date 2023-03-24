package com.harera.hayat.needs.model.medicine;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.framework.model.medicine.MedicineDto;
import com.harera.hayat.framework.model.medicine.MedicineUnitDto;
import com.harera.hayat.needs.model.NeedDto;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class MedicineDonationDto extends NeedDto {

    @JsonProperty("quantity")
    private Float quantity;

    @JsonProperty("medicine_unit")
    private MedicineUnitDto medicineUnit;

    @JsonProperty("medicine")
    private MedicineDto medicine;

    @JsonProperty(value = "city_id")
    private Long cityId;

    @JsonProperty(value = "medicine_unit_id")
    private Long medicineUnitId;

    @JsonProperty(value = "medicine_id")
    private Long medicineId;
}
