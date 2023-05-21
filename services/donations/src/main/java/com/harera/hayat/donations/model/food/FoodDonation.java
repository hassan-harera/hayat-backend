package com.harera.hayat.donations.model.food;

import com.harera.hayat.donations.model.Donation;
import com.harera.hayat.framework.model.food.FoodCategory;
import com.harera.hayat.framework.model.food.FoodUnit;
import com.harera.hayat.framework.model.user.BaseUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "food_donation")
public class FoodDonation extends Donation {

    @Basic
    @Column(name = "food_expiration_date")
    private LocalDate foodExpirationDate;

    @ManyToOne
    @JoinColumn(name = "food_unit_id", referencedColumnName = "id")
    private FoodUnit foodUnit;

    @ManyToOne
    @JoinColumn(name = "food_category_id", referencedColumnName = "id")
    private FoodCategory foodCategory;

    @Basic
    @Column(name = "quantity")
    private Float quantity;

    @ManyToMany
    @JoinTable(name = "food_donation_upvotes",
                    joinColumns = @JoinColumn(name = "donation_id"),
                    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<BaseUser> upvotes = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "food_donation_downvotes",
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
