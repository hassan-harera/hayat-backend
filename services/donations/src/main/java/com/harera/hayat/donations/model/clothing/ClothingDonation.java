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

    private Long clothingConditionId;

    private Long clothingSeasonId;

    private Long clothingSizeId;

    private Long clothingTypeId;
}
