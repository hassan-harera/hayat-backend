package com.harera.hayat.framework.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClothingType {

    @Column(name = "arabic_name")
    private String arabicName;

    @Column(name = "english_name")
    private String englishName;

    private Type type;

    public enum Type {
        WOMEN,
        MEN,
        GIRLS,
        BOYS,
        KIDS,
        MIXED,
    }
}
