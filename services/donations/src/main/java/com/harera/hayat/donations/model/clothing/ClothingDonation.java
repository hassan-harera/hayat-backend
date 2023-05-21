package com.harera.hayat.donations.model.clothing;

import com.harera.hayat.donations.model.Donation;
import com.harera.hayat.framework.model.clothing.ClothingSeason;
import com.harera.hayat.framework.model.clothing.ClothingSize;
import com.harera.hayat.framework.model.clothing.ClothingType;
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

    @Column(name = "clothing_condition_id")
    private Long clothingConditionId;

    @ManyToOne
    @JoinColumn(name = "clothing_season_id", referencedColumnName = "id")
    private ClothingSeason clothingSeason;

    @ManyToOne
    @JoinColumn(name = "clothing_size_id", referencedColumnName = "id")
    private ClothingSize clothingSizeId;

    @ManyToOne
    @JoinColumn(name = "clothing_type_id", referencedColumnName = "id")
    private ClothingType clothingTypeId;
}
