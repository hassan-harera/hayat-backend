package com.harera.hayat.framework.model.medicine;

import com.harera.hayat.framework.model.BaseEntityDto;
import lombok.Data;

@Data
public class MedicineDto extends BaseEntityDto {

    private MedicineCategoryDto category;
    private MedicineUnit unit;
    private String arabicName;
}
