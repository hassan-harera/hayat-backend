package com.harera.hayat.donations.repository.food;

import com.harera.hayat.donations.model.food.FoodDonation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodDonationRepository extends JpaRepository<FoodDonation, Long> {

}
