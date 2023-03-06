package com.harera.hayat.framework.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@AllArgsConstructor
public class ClothingCondition  {

    @Column(name = "arabic_name")
    private String arabicName;

    @Column(name = "english_name")
    private String englishName;

    private Condition condition;

    public enum Condition {
        NEW,
        USED,
        MIXED,
    }
}
