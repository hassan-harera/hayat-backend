package com.harera.hayat.framework.repository.repository;

import com.harera.hayat.framework.model.medicine.MedicineUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MedicineUnitRepository extends JpaRepository<MedicineUnit, Long> {

}
