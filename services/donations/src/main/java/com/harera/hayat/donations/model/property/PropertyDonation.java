package com.harera.hayat.donations.model.property;

import com.harera.hayat.donations.model.Donation;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;


@Setter
@Getter
@Entity
@Table(name = "property_donation")
public class PropertyDonation extends Donation {

    @Column(name = "rooms")
    private int rooms;

    @Column(name = "bathrooms")
    private int bathrooms;

    @Column(name = "kitchens")
    private int kitchens;

    @Column(name = "people_capacity")
    private int peopleCapacity;

    @Column(name = "available_from")
    private OffsetDateTime availableFrom;

    @Column(name = "available_to")
    private OffsetDateTime availableTo;
}
