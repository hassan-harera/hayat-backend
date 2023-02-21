package com.harera.hayat.donations.model;

import com.harera.hayat.framework.model.BaseEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public class BaseDonation extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "donation_id", referencedColumnName = "id")
    private Donation donation;
}
