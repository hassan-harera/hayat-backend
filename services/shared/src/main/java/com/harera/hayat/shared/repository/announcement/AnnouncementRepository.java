package com.harera.hayat.shared.repository.announcement;

import com.harera.hayat.shared.model.announcement.Announcement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends MongoRepository<Announcement, String> {

    @Query("{active: {$eq: ?0}}")
    List<Announcement> findAllByActive(boolean active);
}
