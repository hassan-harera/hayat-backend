package com.harera.hayat.donations.model.medicine;

import com.harera.hayat.framework.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "medicine")
public class Medicine extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private MedicineCategory category;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private MedicineUnit unit;

    @Column(name = "arabic_name")
    private String arabicName;

    @Column(name = "english_name")
    private String englishName;
}
