package com.harera.hayat.needs.model.blood;

import com.harera.hayat.needs.model.Need;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Document(collection = "blood_needs")
public class BloodNeed extends Need {

    @Field("age")
    private int age;

    @Field("blood_type")
    private String bloodType;

    @Field("hospital")
    private String hospital;

    @Field("illness")
    private String illness;
}
