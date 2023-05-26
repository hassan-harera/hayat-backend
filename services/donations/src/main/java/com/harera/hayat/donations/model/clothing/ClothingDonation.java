package com.harera.hayat.donations.model.clothing;

import com.harera.hayat.donations.model.BaseDonation;
import com.harera.hayat.framework.model.clothing.*;
import com.harera.hayat.framework.model.user.BaseUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "clothing_donation")
public class ClothingDonation extends BaseDonation {

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clothing_season_id", referencedColumnName = "id")
    private ClothingSeason clothingSeason;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clothing_size_id", referencedColumnName = "id")
    private ClothingSize clothingSize;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clothing_type_id", referencedColumnName = "id")
    private ClothingType clothingType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clothing_category_id", referencedColumnName = "id")
    private ClothingCategory clothingCategory;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clothing_condition_id", referencedColumnName = "id")
    private ClothingCondition clothingCondition;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "clothing_donation_upvotes",
                    joinColumns = @JoinColumn(name = "donation_id"),
                    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<BaseUser> upvotes = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "clothing_donation_downvotes",
                    joinColumns = @JoinColumn(name = "donation_id"),
                    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<BaseUser> downvotes = new HashSet<>();

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
