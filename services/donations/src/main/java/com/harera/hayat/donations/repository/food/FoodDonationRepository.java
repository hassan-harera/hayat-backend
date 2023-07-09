package com.harera.hayat.donations.repository.food;

import com.harera.hayat.donations.model.food.FoodDonation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodDonationRepository extends JpaRepository<FoodDonation, Long> {

    @Query("SELECT d FROM FoodDonation d WHERE d.status = 'ACTIVE' "
            + "AND d.donationExpirationDate > CURRENT_TIMESTAMP "
            + "AND (d.title LIKE %:query% OR d.description LIKE %:query%)")
    List<FoodDonation> search(@Param("query") String query, Pageable withPage);
}
