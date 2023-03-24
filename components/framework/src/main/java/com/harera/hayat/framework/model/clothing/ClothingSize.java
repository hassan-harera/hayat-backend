package com.harera.hayat.framework.model.clothing;

import com.harera.hayat.framework.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "clothing_size")
public class ClothingSize extends BaseEntity {

    @Column(name = "arabic_name")
    private String arabicName;

    @Column(name = "english_name")
    private String englishName;

    @Enumerated(EnumType.STRING)
    @Column(name = "shortcut")
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
