package com.harera.hayat.needs.repository.medicine;

import com.harera.hayat.needs.model.medicine.MedicineNeed;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineNeedRepository extends MongoRepository<MedicineNeed, Long> {

}
