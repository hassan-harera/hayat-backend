package com.harera.hayat.framework.model.clothing;

import com.harera.hayat.framework.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "clothing_type")
public class ClothingType extends BaseEntity {

    @Column(name = "arabic_name")
    private String arabicName;

    @Column(name = "english_name")
    private String englishName;

    @Enumerated(EnumType.STRING)
    @Column(name = "shortcut")
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
