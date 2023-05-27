package com.harera.hayat.needs.controller.books;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.model.user.BaseUserDto;
import com.harera.hayat.needs.ApplicationIT;
import com.harera.hayat.needs.model.CommunicationMethod;
import com.harera.hayat.needs.model.NeedCategory;
import com.harera.hayat.needs.model.NeedStatus;
import com.harera.hayat.needs.model.book.BookNeedRequest;
import com.harera.hayat.needs.model.book.BookNeedResponse;
import com.harera.hayat.needs.stubs.CityStubs;
import com.harera.hayat.needs.stubs.book.BookNeedStubs;
import com.harera.hayat.needs.util.RequestUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class BoolNeedControllerIT extends ApplicationIT {

    private final RequestUtil requestUtil;
    private final BookNeedStubs bookNeedStubs;
    private final CityStubs cityStubs;

    @Test
    void create() {
        City city = cityStubs.insert("arabicName", "englishName");

        BookNeedRequest bookNeedRequest = new BookNeedRequest();
        bookNeedRequest.setTitle("title");
        bookNeedRequest.setDescription("description");
        bookNeedRequest.setCommunicationMethod(CommunicationMethod.PHONE);
        bookNeedRequest.setCityId(city.getId());
        bookNeedRequest.setUser(new BaseUserDto());
        bookNeedRequest.setBookName("bookName");
        bookNeedRequest.setBookAuthor("bookAuthor");
        bookNeedRequest.setBookPublisher("bookPublisher");
        bookNeedRequest.setBookLanguage("bookLanguage");

        ResponseEntity<BookNeedResponse> bookNeedResponseResponseEntity
                = requestUtil.post("/api/v1/needs/book", bookNeedRequest, null, BookNeedResponse.class);

        BookNeedResponse body = bookNeedResponseResponseEntity.getBody();

        assertEquals(200, bookNeedResponseResponseEntity.getStatusCode().value());
        assertNotNull(body);
        assertEquals(NeedCategory.BOOKS, body.getCategory());
        assertEquals(NeedStatus.PENDING, body.getStatus());
        assertEquals(bookNeedRequest.getTitle(), body.getTitle());
        assertEquals(bookNeedRequest.getDescription(), body.getDescription());
        assertNotNull(body.getNeedDate());
        assertNotNull(body.getNeedExpirationDate());
        assertEquals(bookNeedRequest.getCommunicationMethod(), body.getCommunicationMethod());
        assertEquals(bookNeedRequest.getCityId(), body.getCity().getId());
    }
}
