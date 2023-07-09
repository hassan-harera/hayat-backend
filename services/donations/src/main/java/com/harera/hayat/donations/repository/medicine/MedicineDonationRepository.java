package com.harera.hayat.donations.repository.medicine;

import com.harera.hayat.donations.model.medicine.MedicineDonation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineDonationRepository
                extends JpaRepository<MedicineDonation, Long> {

    @Query("SELECT d FROM MedicineDonation d WHERE d.status = 'ACTIVE' "
            + "AND d.donationExpirationDate > CURRENT_TIMESTAMP "
            + "AND (d.title LIKE %:query% OR d.description LIKE %:query%)")
    List<MedicineDonation> search(String query, Pageable pageable);
}
