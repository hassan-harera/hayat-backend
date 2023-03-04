package com.harera.hayat.donations.model;

import com.harera.hayat.framework.model.BaseEntity;
import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.model.user.User;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Setter
@Getter
@MappedSuperclass
public class Donation extends BaseEntity {

    @Basic
    @Column(name = "title")
    private String title;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "donation_date")
    private OffsetDateTime donationDate;

    @Basic
    @Column(name = "donation_expiration_date")
    private OffsetDateTime donationExpirationDate;

    @Column(name = "communication_method")
    private CommunicationMethod communicationMethod;

    @Column(name = "category")
    private DonationCategory category;

    @Column(name = "status")
    private DonationState status;

    @ManyToOne(targetEntity = City.class)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
