package com.harera.hayat.needs.model.medicine;

import com.harera.hayat.framework.model.medicine.MedicineDto;
import com.harera.hayat.framework.model.medicine.MedicineUnitDto;
import com.harera.hayat.needs.model.Need;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Document("medicine_need")
public class MedicineDonation extends Need {

    @Field(name = "quantity")
    private Float quantity;

    @Field(name = "medicine_unit")
    private MedicineUnitDto medicineUnit;

    @ManyToOne
    @JoinColumn(name = "medicine_id", referencedColumnName = "id")
    private MedicineDto medicine;
}
