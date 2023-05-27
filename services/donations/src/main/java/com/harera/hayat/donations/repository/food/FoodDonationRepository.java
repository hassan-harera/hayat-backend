package com.harera.hayat.donations.repository.food;

import com.harera.hayat.donations.model.food.FoodDonation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodDonationRepository extends JpaRepository<FoodDonation, Long> {

    @Query("select d from FoodDonation d where d.status = 'ACTIVE' " +
            "and (d.title like %?1% or d.description like %?1%) " +
            "order by d.donationDate desc")
    List<FoodDonation> search(String query, Pageable withPage);
}
