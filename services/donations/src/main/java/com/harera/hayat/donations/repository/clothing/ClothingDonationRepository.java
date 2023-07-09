package com.harera.hayat.donations.repository.clothing;

import com.harera.hayat.donations.model.clothing.ClothingDonation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClothingDonationRepository
                extends JpaRepository<ClothingDonation, Long> {

    @Query("SELECT d FROM ClothingDonation d WHERE d.status = 'ACTIVE' "
            + "AND d.donationExpirationDate > CURRENT_TIMESTAMP "
            + "AND (d.title LIKE %:query% OR d.description LIKE %:query%)")
    List<ClothingDonation> search(@Param("query") String query, PageRequest of);
}
