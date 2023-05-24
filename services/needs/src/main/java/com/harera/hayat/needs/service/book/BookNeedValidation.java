package com.harera.hayat.needs.service.book;

import org.springframework.stereotype.Service;

import com.harera.hayat.framework.repository.city.CityRepository;
import com.harera.hayat.needs.model.book.BookNeedRequest;
import com.harera.hayat.needs.service.NeedValidation;

@Service
public class BookNeedValidation extends NeedValidation {

    public BookNeedValidation(CityRepository cityRepository) {
        super(cityRepository);
    }

    public void validate(BookNeedRequest bookNeedRequest) {
        super.validate(bookNeedRequest);
    }
}
