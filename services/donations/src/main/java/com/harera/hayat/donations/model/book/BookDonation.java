package com.harera.hayat.donations.model.book;

import com.harera.hayat.donations.model.Donation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "book_donation")
public class BookDonation extends Donation {

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "book_sub_title")
    private String bookSubTitle;

    @Column(name = "book_author")
    private String bookAuthor;

    @Column(name = "book_publisher")
    private String bookPublisher;

    @Column(name = "book_publication_year")
    private String publicationYear;

    @Column(name = "book_language")
    private String bookLanguage;
}
