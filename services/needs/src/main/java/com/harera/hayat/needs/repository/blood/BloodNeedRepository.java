package com.harera.hayat.needs.repository.blood;

import com.harera.hayat.needs.model.NeedStatus;
import com.harera.hayat.needs.model.blood.BloodNeed;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodNeedRepository extends MongoRepository<BloodNeed, String> {

    @Query("{ $or: [ {title: {$regex: ?0}}, {description: {$regex: ?0}}], $and:[ {status: {$eq: ?1}}]}")
    List<BloodNeed> search(String title, NeedStatus status, Pageable pageable);
}
