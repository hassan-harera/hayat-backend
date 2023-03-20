package com.harera.hayat.donations.controller;

import com.harera.hayat.donations.ApplicationIT;
import com.harera.hayat.donations.model.CommunicationMethod;
import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.DonationStatus;
import com.harera.hayat.donations.model.book.BookDonation;
import com.harera.hayat.donations.model.book.BookDonationRequest;
import com.harera.hayat.donations.model.book.BookDonationResponse;
import com.harera.hayat.donations.stubs.book.BookDonationStubs;
import com.harera.hayat.donations.stubs.city.CityStubs;
import com.harera.hayat.donations.util.DataUtil;
import com.harera.hayat.donations.util.RequestUtil;
import com.harera.hayat.framework.model.city.City;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class BookDonationControllerIT extends ApplicationIT {

    private final RequestUtil requestUtil;
    private final CityStubs cityStubs;
    private final DataUtil dataUtil;
    private final BookDonationStubs bookDonationStubs;

    @Test
    void create_withValidBookDonationRequest_thenValidateResponse() {
        // Given
        String url = "/api/v1/donations/book";

        City city = cityStubs.insert("arabic_name", "english_name");

        BookDonationRequest request = new BookDonationRequest();
        request.setTitle("title");
        request.setDescription("description");
        request.setAmount(2);
        request.setBookTitle("book title");
        request.setCityId(city.getId());
        request.setCommunicationMethod(CommunicationMethod.CHAT);

        try {
            // When
            ResponseEntity<BookDonationResponse> responseEntity = requestUtil.post(url,
                            request, null, BookDonationResponse.class);

            // Then
            Assertions.assertEquals(200, responseEntity.getStatusCode().value());

            BookDonationResponse response = responseEntity.getBody();
            Assertions.assertNotNull(response);
            Assertions.assertNotNull(response.getId());
            Assertions.assertEquals(request.getTitle(), response.getTitle());
            Assertions.assertEquals(request.getDescription(), response.getDescription());
            Assertions.assertEquals(request.getAmount(), response.getAmount());
            Assertions.assertEquals(request.getCommunicationMethod(),
                            response.getCommunicationMethod());
            Assertions.assertEquals(request.getCityId(), response.getCity().getId());

            BookDonation bookDonation = bookDonationStubs.get(response.getId());
            dataUtil.delete(bookDonation);
        } finally {
            // Cleanup
            dataUtil.delete(city);
        }
    }

    @Test
    void update_withValidBookDonationRequest_thenValidateResponse() {
        // Given
        String url = "/api/v1/donations/book/%d";

        City city = cityStubs.insert("arabic_name", "english_name");

        BookDonation bookDonation =
                        bookDonationStubs.insert(1, "title", DonationCategory.FOOD,
                                        "description", city, DonationStatus.PENDING);

        BookDonationRequest request = new BookDonationRequest();
        request.setCityId(city.getId());
        request.setAmount(2);
        request.setBookTitle("new_title");
        request.setTitle("new_title");
        request.setDescription("new_desc");
        request.setCommunicationMethod(CommunicationMethod.CHAT);

        try {
            // When
            ResponseEntity<BookDonationResponse> responseEntity =
                            requestUtil.put(url.formatted(bookDonation.getId()), request,
                                            null, BookDonationResponse.class);

            // Then
            Assertions.assertEquals(200, responseEntity.getStatusCodeValue());

            BookDonationResponse response = responseEntity.getBody();
            Assertions.assertNotNull(response);

            Assertions.assertEquals(request.getTitle(), response.getTitle());
            Assertions.assertEquals(request.getDescription(), response.getDescription());
            Assertions.assertEquals(request.getAmount(), response.getAmount());
            Assertions.assertEquals(request.getCommunicationMethod(),
                            response.getCommunicationMethod());
            Assertions.assertEquals(request.getCityId(), response.getCity().getId());
        } finally {
            // Cleanup
            dataUtil.delete(city, bookDonation);
        }
    }

    @Test
    void get_thenValidateResponse() {
        // Given
        String url = "/api/v1/donations/book/%d";

        City city = cityStubs.insert("arabic_name", "english_name");

        BookDonation bookDonation =
                        bookDonationStubs.insert(1, "title", DonationCategory.FOOD,
                                        "description", city, DonationStatus.PENDING);

        try {
            // When
            ResponseEntity<BookDonationResponse> responseEntity =
                            requestUtil.get(url.formatted(bookDonation.getId()), null,
                                            null, BookDonationResponse.class);

            // Then
            Assertions.assertEquals(200, responseEntity.getStatusCodeValue());

            BookDonationResponse response = responseEntity.getBody();
            Assertions.assertNotNull(response);

            Assertions.assertEquals(bookDonation.getTitle(), response.getTitle());
            Assertions.assertEquals(bookDonation.getDescription(), response.getDescription());
            Assertions.assertEquals(bookDonation.getCommunicationMethod(),
                            response.getCommunicationMethod());
            Assertions.assertEquals(bookDonation.getCity().getId(),
                            response.getCity().getId());
            Assertions.assertEquals(bookDonation.getAmount(), response.getAmount());
        } finally {
            // Cleanup
            dataUtil.delete(city, bookDonation);
        }
    }
}
