package com.harera.hayat.donations.repository.book;

import com.harera.hayat.donations.model.book.BookDonation;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDonationRepository extends JpaRepository<BookDonation, Long> {


    @Query("SELECT d FROM BookDonation d WHERE d.status = 'ACTIVE' "
            + "AND d.donationExpirationDate > CURRENT_TIMESTAMP "
            + "AND (d.title LIKE %:query% OR d.description LIKE %:query%)")
    List<BookDonation> search(@Param("query") String query, Pageable withPage);
}