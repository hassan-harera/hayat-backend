package com.harera.hayat.donations.model.book;

import com.harera.hayat.donations.model.Donation;
import com.harera.hayat.framework.model.user.BaseUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "book_donation")
public class BookDonation extends Donation {

    @Column(name = "quantity")
    private Integer quantity;

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

    @ManyToMany
    @JoinTable(name = "book_donation_upvotes",
                    joinColumns = @JoinColumn(name = "donation_id"),
                    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<BaseUser> upvotes = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "book_donation_downvotes",
                    joinColumns = @JoinColumn(name = "donation_id"),
                    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<BaseUser> downvotes = new HashSet<>();

    public Integer getReputation() {
        return upvotes.size() - downvotes.size();
    }

    public void upvote(BaseUser user) {
        upvotes.add(user);
        downvotes.remove(user);
    }

    public void downvote(BaseUser user) {
        downvotes.add(user);
        upvotes.remove(user);
    }
}
