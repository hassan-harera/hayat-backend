package com.harera.hayat.donations.repository.book;

import com.harera.hayat.donations.model.book.BookDonation;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDonationRepository extends JpaRepository<BookDonation, Long> {


    @Query("select e from BookDonation e where e.title like  %?1% or e.description like %?1% order by e.donationDate desc")
    List<BookDonation> search(String query, Pageable withPage);
}