package com.harera.hayat.needs.stubs.book;


import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.model.user.User;
import com.harera.hayat.needs.model.CommunicationMethod;
import com.harera.hayat.needs.model.NeedCategory;
import com.harera.hayat.needs.model.NeedState;
import com.harera.hayat.needs.model.books.BookNeed;
import com.harera.hayat.needs.repository.books.BookNeedRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookNeedStubs {

    private final BookNeedRepository bookNeedRepository;

    public BookNeed insert(
            String title,
            String description,
            LocalDateTime needDate,
            LocalDateTime needExpirationDate,
            NeedCategory category,
            NeedState status,
            CommunicationMethod communicationMethod,
            City city,
            User user,
            String bookName,
            String bookAuthor,
            String bookPublisher,
            String bookLanguage
    ) {
        BookNeed bookNeed = create(
                title,
                description,
                needDate,
                needExpirationDate,
                category,
                status,
                communicationMethod,
                city,
                user,
                bookName,
                bookAuthor,
                bookPublisher,
                bookLanguage
        );
        return bookNeedRepository.save(bookNeed);
    }

    public BookNeed create(
            String title,
            String description,
            LocalDateTime needDate,
            LocalDateTime needExpirationDate,
            NeedCategory category,
            NeedState status,
            CommunicationMethod communicationMethod,
            City city,
            User user,
            String bookName,
            String bookAuthor,
            String bookPublisher,
            String bookLanguage
    ) {
        BookNeed bookNeed = new BookNeed();
        // set all parameters
        bookNeed.setTitle(title);
        bookNeed.setDescription(description);
        bookNeed.setNeedDate(needDate);
        bookNeed.setNeedExpirationDate(needExpirationDate);
        bookNeed.setCategory(category);
        bookNeed.setStatus(status);
        bookNeed.setCommunicationMethod(communicationMethod);
        bookNeed.setCity(city);
        bookNeed.setUser(user);
        bookNeed.setBookName(bookName);
        bookNeed.setBookAuthor(bookAuthor);
        bookNeed.setBookPublisher(bookPublisher);
        bookNeed.setBookLanguage(bookLanguage);
        return bookNeed;
    }
}
