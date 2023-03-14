package com.harera.hayat.needs.repository.books;

import com.harera.hayat.needs.model.books.BookNeed;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookNeedRepository extends MongoRepository<BookNeed, String> {
}
