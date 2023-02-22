package com.harera.hayat.needs.model.possession;

import com.harera.hayat.needs.model.Need;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "possession_need")
public class PossessionNeed extends Need {

    @ManyToOne
    @JoinColumn(name = "possession_category_id", referencedColumnName = "id")
    private PossessionCategory possessionCategory;

    @ManyToOne
    @JoinColumn(name = "possession_condition_id", referencedColumnName = "id")
    private PossessionCondition possessionCondition;
}
