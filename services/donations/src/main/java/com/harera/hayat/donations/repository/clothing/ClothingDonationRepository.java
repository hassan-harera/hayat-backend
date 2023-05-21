package com.harera.hayat.donations.repository.clothing;

import com.harera.hayat.donations.model.clothing.ClothingDonation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClothingDonationRepository
                extends JpaRepository<ClothingDonation, Long> {

    @Query("SELECT c FROM ClothingDonation c WHERE c.title LIKE %?1% OR c.description LIKE %?1%")
    List<ClothingDonation> search(String query, PageRequest of);
}
