package com.harera.hayat.needs.repository.blood;

import com.harera.hayat.needs.model.need.BloodNeed;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodNeedRepository extends MongoRepository<BloodNeed, String> {
}
