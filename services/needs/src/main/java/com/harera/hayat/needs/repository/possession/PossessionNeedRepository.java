package com.harera.hayat.needs.repository.possession;

import com.harera.hayat.needs.model.possession.PossessionNeed;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PossessionNeedRepository extends MongoRepository<PossessionNeed, Long> {
}
