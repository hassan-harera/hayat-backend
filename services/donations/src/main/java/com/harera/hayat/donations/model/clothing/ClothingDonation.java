package com.harera.hayat.donations.model.clothing;

import com.harera.hayat.donations.model.Donation;
import com.harera.hayat.framework.model.ClothingCondition;
import com.harera.hayat.framework.model.ClothingType;
import com.harera.hayat.framework.model.ClothingSeason;
import com.harera.hayat.framework.model.ClothingSize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "clothing_donation")
public class ClothingDonation extends Donation {

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "clothing_condition_id", referencedColumnName = "id")
    private ClothingCondition clothingCondition;

    @ManyToOne
    @JoinColumn(name = "clothing_season_id", referencedColumnName = "id")
    private ClothingSeason clothingSeason;

    @ManyToOne
    @JoinColumn(name = "clothing_size_id", referencedColumnName = "id")
    private ClothingSize clothingSize;

    @ManyToOne
    @JoinColumn(name = "clothing_category_id", referencedColumnName = "id")
    private ClothingType clothingType;
}
