package com.harera.hayat.donations.model.medicine;

import com.harera.hayat.framework.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "medicine_category")
public class MedicineCategory extends BaseEntity {

    @Basic
    @Column(name = "arabic_name")
    private String arabicName;

    @Basic
    @Column(name = "english_name")
    private String englishName;
}
