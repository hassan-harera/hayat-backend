package com.harera.hayat.needs.repository.books;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harera.hayat.needs.model.books.BookNeed;

public interface BookNeedRepository extends JpaRepository<BookNeed, Long> {
}
