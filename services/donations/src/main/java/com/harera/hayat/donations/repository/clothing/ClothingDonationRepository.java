package com.harera.hayat.donations.repository.clothing;

import com.harera.hayat.donations.model.clothing.ClothingDonation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClothingDonationRepository extends JpaRepository<ClothingDonation, Long> {
}