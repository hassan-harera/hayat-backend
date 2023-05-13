package com.harera.hayat.donations.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.framework.model.BaseEntity;
import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.model.user.BaseUser;
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
    @Enumerated(EnumType.STRING)
    private CommunicationMethod communicationMethod;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private DonationCategory category;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DonationStatus status;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(targetEntity = City.class)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @ManyToOne(targetEntity = BaseUser.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private BaseUser user;

    @Column(name = "telegram_link")
    private String telegramLink;

    @Column(name = "whatsapp_link")
    private String whatsappLink;
}
