package com.harera.hayat.needs.model.possession;

import com.harera.hayat.needs.model.Need;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Document("possession_need")
public class PossessionNeed extends Need {

    @Field(name = "possession_category")
    private PossessionCategory possessionCategory;

    @Field(name = "possession_condition")
    private PossessionCondition possessionCondition;
}
