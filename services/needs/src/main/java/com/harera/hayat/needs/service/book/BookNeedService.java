package com.harera.hayat.needs.service.book;

import com.harera.hayat.framework.exception.MandatoryFieldException;
import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.repository.city.CityRepository;
import com.harera.hayat.framework.util.ObjectMapperUtils;
import com.harera.hayat.needs.model.NeedCategory;
import com.harera.hayat.needs.model.NeedState;
import com.harera.hayat.needs.model.books.BookNeed;
import com.harera.hayat.needs.model.books.BookNeedRequest;
import com.harera.hayat.needs.model.books.BookNeedResponse;
import com.harera.hayat.needs.repository.books.BookNeedRepository;
import com.harera.hayat.needs.util.ErrorCode;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class BookNeedService {

    private final CityRepository cityRepository;
    private final BookNeedValidation bookNeedValidation;
    private final ModelMapper modelMapper;
    private final BookNeedRepository bookNeedRepository;

    public BookNeedService(CityRepository cityRepository,
                    BookNeedValidation bookNeedValidation, ModelMapper modelMapper,
                    BookNeedRepository bookNeedRepository) {
        this.cityRepository = cityRepository;
        this.bookNeedValidation = bookNeedValidation;
        this.modelMapper = modelMapper;
        this.bookNeedRepository = bookNeedRepository;
    }

    /**
     * Create Flow
     * validate request
     * map to entity
     * set date
     * set expiration date
     * set user from request header
     * set city from id
     * set status to pending
     * send to AI model to process the request
     * set category to book
     * save to db
     * map to response
     */
    public BookNeedResponse create(BookNeedRequest bookNeedRequest) {
        bookNeedValidation.validate(bookNeedRequest);

        BookNeed bookNeed = modelMapper.map(bookNeedRequest, BookNeed.class);
        bookNeed.setNeedDate(LocalDateTime.now());
        bookNeed.setNeedExpirationDate(LocalDateTime.now().plusDays(45));
        bookNeed.setCategory(NeedCategory.BOOKS);
        bookNeed.setCity(getCity(bookNeedRequest.getCityId()));
        bookNeed.setStatus(NeedState.PENDING);
        // TODO: send to AI model to process the request
        // TODO: set user from request header

        var bookNeedResponse = bookNeedRepository.save(bookNeed);
        return modelMapper.map(bookNeedResponse, BookNeedResponse.class);
    }

    private City getCity(Long cityId) {
        return cityRepository.findById(cityId)
                        .orElseThrow(() -> new MandatoryFieldException(
                                        ErrorCode.MANDATORY_NEED_CITY_ID, "city_id"));
    }

    public List<BookNeedResponse> list() {
        List<BookNeed> all = bookNeedRepository.findAll();
        return ObjectMapperUtils.mapAll(all, BookNeedResponse.class);
    }
}
