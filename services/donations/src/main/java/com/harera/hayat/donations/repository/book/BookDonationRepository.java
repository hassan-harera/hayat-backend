package com.harera.hayat.donations.repository.book;

import com.harera.hayat.donations.model.book.BookDonation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDonationRepository extends JpaRepository<BookDonation, Long> {
}