package com.harera.hayat.donations.repository.medicine;

import com.harera.hayat.donations.model.medicine.MedicineUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MedicineUnitRepository extends JpaRepository<MedicineUnit, Long> {

}
