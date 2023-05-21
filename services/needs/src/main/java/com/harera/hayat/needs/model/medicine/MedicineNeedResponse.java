package com.harera.hayat.needs.model.medicine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true,
                value = { "city_id", "user_id", "medicine_unit_id", "medicine_id" })
public class MedicineNeedResponse extends MedicineNeedDto {

}
