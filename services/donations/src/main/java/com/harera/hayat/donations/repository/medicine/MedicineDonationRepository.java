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

    @Query(value = "SELECT m FROM MedicineDonation m WHERE m.title LIKE %?1% " +
            "OR m.description LIKE %?1% " +
            "OR m.medicine.arabicName LIKE %?1% " +
            "OR m.medicine.englishName LIKE %?1% " +
            "OR m.medicine.englishName LIKE %?1%")
    List<MedicineDonation> search(String query, Pageable pageable);
}
