package com.harera.hayat.needs.repository.book;

import com.harera.hayat.needs.model.NeedStatus;
import com.harera.hayat.needs.model.book.BookNeed;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookNeedRepository extends MongoRepository<BookNeed, String> {

    @Query("{ $or: [ {title: {$regex: ?0}}, {description: {$regex: ?0}}], $and:[ {status: {$eq: ?1}}]}")
    List<BookNeed> search(String title, NeedStatus status, PageRequest page);
}
