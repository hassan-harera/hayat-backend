package com.harera.hayat.framework.model.medicine;

import com.harera.hayat.framework.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Setter
@Getter
@Table(name = "medicine_unit")
public class MedicineUnit extends BaseEntity {

    @Basic
    @Column(name = "arabic_name")
    private String arabicName;

    @Basic
    @Column(name = "english_name")
    private String englishName;
}
