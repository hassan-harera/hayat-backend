package com.harera.hayat.donations.service.image;

import com.harera.hayat.donations.model.image.DonationImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationImageRepository extends JpaRepository<DonationImage, Long> {
}
