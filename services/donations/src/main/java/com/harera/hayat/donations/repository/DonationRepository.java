package com.harera.hayat.donations.repository;

import com.harera.hayat.donations.model.Donation;
import com.harera.hayat.donations.model.food.FoodDonation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("SELECT d FROM Donation d WHERE d.status = 'ACTIVE' "
                    + "AND d.donationExpirationDate > CURRENT_TIMESTAMP "
                    + "AND (d.title LIKE %:query% OR d.description LIKE %:query%)")
    List<Donation> search(@Param("query") String query, Pageable withPage);

    @Query("SELECT d FROM Donation d WHERE d.qrCode = :qrCode")
    Optional<Donation> findByQrCode(@Param("qrCode") String qrCode);
}
