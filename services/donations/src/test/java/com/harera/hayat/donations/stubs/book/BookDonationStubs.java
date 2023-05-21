package com.harera.hayat.donations.stubs.book;

import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.DonationStatus;
import com.harera.hayat.donations.model.book.BookDonation;
import com.harera.hayat.donations.repository.book.BookDonationRepository;
import com.harera.hayat.framework.model.city.City;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookDonationStubs {

    private final BookDonationRepository bookDonationRepository;

    public BookDonation insert(Integer amount, String title, DonationCategory category,
                    String description, City city, DonationStatus state) {
        BookDonation bookDonation =
                        create(amount, title, category, description, city, state);
        return bookDonationRepository.save(bookDonation);
    }

    public BookDonation create(Integer amount, String title, DonationCategory category,
                    String description, City city, DonationStatus state) {
        BookDonation bookDonation = new BookDonation();
        bookDonation.setId(0L);
        bookDonation.setQuantity(amount);
        bookDonation.setTitle(title);
        bookDonation.setCategory(category);
        bookDonation.setCity(city);
        bookDonation.setStatus(state);
        return bookDonation;
    }

    public BookDonation get(Long id) {
        return bookDonationRepository.findById(id).orElse(null);
    }
}
