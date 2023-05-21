package com.harera.hayat.needs.model.medicine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = { "id", "city", "user", "city",
        "user", "medicine", "medicine_unit" })
public class MedicineNeedUpdateRequest extends MedicineNeedDto {

    @JsonProperty(value = "city_id")
    private Long cityId;

    @JsonProperty(value = "medicine_unit_id")
    private Long medicineUnitId;

    @JsonProperty(value = "medicine_id")
    private Long medicineId;
}
