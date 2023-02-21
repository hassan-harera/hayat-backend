package com.harera.hayat.donations.model.image;

import com.harera.hayat.donations.model.Donation;
import com.harera.hayat.framework.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "donation_images")
public class DonationImage extends BaseEntity {

    @Basic
    @Column(name = "url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donation_id", referencedColumnName = "id")
    private Donation donation;
}
