package com.harera.hayat.donations.repository.property;

import com.harera.hayat.donations.model.property.PropertyDonation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyDonationRepository
                extends JpaRepository<PropertyDonation, Long> {
}
