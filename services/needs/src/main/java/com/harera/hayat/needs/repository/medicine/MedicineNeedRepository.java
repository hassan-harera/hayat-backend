package com.harera.hayat.needs.repository.medicine;

import com.harera.hayat.needs.model.medicine.MedicineNeed;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineNeedRepository extends MongoRepository<MedicineNeed, String> {

    int countById(String id);

    List<MedicineNeed> searchByTitleRegexOrDescriptionRegex(String title, String description);
}
