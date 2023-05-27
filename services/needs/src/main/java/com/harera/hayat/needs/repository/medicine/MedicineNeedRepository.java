package com.harera.hayat.needs.repository.medicine;

import com.harera.hayat.needs.model.NeedStatus;
import com.harera.hayat.needs.model.medicine.MedicineNeed;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineNeedRepository extends MongoRepository<MedicineNeed, String> {

    int countById(String id);

    @Query("{ $or: [ {title: {$regex: ?0}}, {description: {$regex: ?0}}], $and:[ {status: {$eq: ?1}}]}")
    List<MedicineNeed> search(String title, NeedStatus status, PageRequest page);
}
