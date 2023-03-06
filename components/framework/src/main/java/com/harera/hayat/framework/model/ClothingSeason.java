package com.harera.hayat.framework.model;

import com.harera.hayat.framework.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@AllArgsConstructor
public class ClothingSeason {

    @Column(name = "arabic_name")
    private String arabicName;

    @Column(name = "english_name")
    private String englishName;

    private Season season;

    public enum Season {
        WINTER,
        SUMMER,
        AUTUMN,
        SPRING,
    }
}
