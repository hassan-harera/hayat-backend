package com.harera.hayat.donations.model.clothing;

import com.harera.hayat.donations.model.Donation;
import com.harera.hayat.framework.model.clothing.ClothingSeason;
import com.harera.hayat.framework.model.clothing.ClothingSize;
import com.harera.hayat.framework.model.clothing.ClothingType;
import com.harera.hayat.framework.model.user.BaseUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

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

    @ManyToMany
    @JoinTable(name = "clothing_donation_upvotes", joinColumns = @JoinColumn(name = "donation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<BaseUser> upvotes;

    @ManyToMany
    @JoinTable(name = "clothing_donation_downvotes", joinColumns = @JoinColumn(name = "donation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<BaseUser> downvotes;

    public Integer getReputation() {
        return upvotes.size() - downvotes.size();
    }

    public void upvote(BaseUser user) {
        upvotes.add(user);
        downvotes.remove(user);
    }

    public void downvote(BaseUser user) {
        downvotes.add(user);
        upvotes.remove(user);
    }
}
