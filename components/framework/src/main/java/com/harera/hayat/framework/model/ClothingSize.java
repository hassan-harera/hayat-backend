package com.harera.hayat.framework.model;

import com.harera.hayat.framework.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class ClothingSize {

    @Column(name = "arabic_name")
    private String arabicName;

    @Column(name = "english_name")
    private String englishName;

    private Size size;

    public enum Size {
        XS,
        S,
        M,
        L,
        XL,
        XXL,
        XXXL,
    }
}
