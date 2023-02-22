package com.harera.hayat.needs.model.possession;

import com.harera.hayat.framework.model.BaseEntity;
import com.harera.hayat.needs.model.Need;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "possession_category")
public class PossessionCategory extends BaseEntity {

    @Column(name = "arabic_name")
    private String arabicName;

    @Column(name = "english_name")
    private String englishName;
}
