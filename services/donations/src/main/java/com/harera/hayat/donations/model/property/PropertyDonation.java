package com.harera.hayat.donations.model.property;

import com.harera.hayat.donations.model.Donation;
import com.harera.hayat.framework.model.user.BaseUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;


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

    @ManyToMany
    @JoinTable(name = "property_donation_upvotes", joinColumns = @JoinColumn(name = "donation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<BaseUser> upvotes = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "property_donation_downvotes", joinColumns = @JoinColumn(name = "donation_id"),
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
