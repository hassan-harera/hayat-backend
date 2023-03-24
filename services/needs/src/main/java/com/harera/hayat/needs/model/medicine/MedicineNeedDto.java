package com.harera.hayat.needs.model.medicine;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.framework.model.medicine.MedicineDto;
import com.harera.hayat.framework.model.medicine.MedicineUnitDto;
import com.harera.hayat.needs.model.NeedDto;
import lombok.Data;

@Data
public class MedicineNeedDto extends NeedDto {

    @JsonProperty("quantity")
    private Float quantity;

    @JsonProperty("medicine_unit")
    private MedicineUnitDto medicineUnit;

    @JsonProperty("medicine")
    private MedicineDto medicine;
}
