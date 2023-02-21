package com.harera.hayat.donations.model.food;

import com.harera.hayat.donations.model.Donation;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Setter
@Getter
@Entity
@Table(name = "food_donation")
public class FoodDonation extends Donation {

    @Basic
    @Column(name = "food_expiration_date")
    private OffsetDateTime foodExpirationDate;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private FoodUnit unit;

    @Basic
    @Column(name = "amount")
    private Float amount;

    @OneToOne
    @JoinColumn(name = "donation_id", referencedColumnName = "id")
    private Donation donation;
}
