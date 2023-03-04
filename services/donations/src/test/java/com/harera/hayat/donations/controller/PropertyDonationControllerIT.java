package com.harera.hayat.donations.controller;

import com.harera.hayat.donations.ApplicationIT;
import com.harera.hayat.donations.model.CommunicationMethod;
import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.DonationState;
import com.harera.hayat.donations.model.property.PropertyDonation;
import com.harera.hayat.donations.model.property.PropertyDonationRequest;
import com.harera.hayat.donations.model.property.PropertyDonationResponse;
import com.harera.hayat.donations.model.property.PropertyDonationUpdateRequest;
import com.harera.hayat.donations.stubs.city.CityStubs;
import com.harera.hayat.donations.stubs.property.PropertyDonationStubs;
import com.harera.hayat.donations.util.DataUtil;
import com.harera.hayat.donations.util.RequestUtil;
import com.harera.hayat.framework.model.city.City;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class PropertyDonationControllerIT extends ApplicationIT {

    private final RequestUtil requestUtil;
    private final CityStubs cityStubs;
    private final DataUtil dataUtil;
    private final PropertyDonationStubs propertyDonationStubs;

    @Test
    void create_withValidPropertyDonationRequest_thenValidateResponse() {
        // Given
        String url = "/api/v1/donations/property";

        City city = cityStubs.insert("arabic_name", "english_name");

        PropertyDonationRequest request = new PropertyDonationRequest();
        request.setId(0L);
        request.setTitle("title");
        request.setDescription("description");
        request.setDonationDate(OffsetDateTime.now());
        request.setDonationExpirationDate(OffsetDateTime.now());
        request.setCategory(DonationCategory.PROPERTY);
        request.setStatus(DonationState.ACCEPTED);
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setCityId(city.getId());
        request.setBathrooms(1);
        request.setKitchens(1);
        request.setRooms(5);
        request.setPeopleCapacity(15);
        request.setAvailableFrom(OffsetDateTime.now().plusHours(1));
        request.setAvailableTo(OffsetDateTime.now().plusMonths(1));

        ResponseEntity<PropertyDonationResponse> responseEntity = null;
        PropertyDonationResponse response = null;
        try {
            // When
            responseEntity = requestUtil.post(url, request, null,
                            PropertyDonationResponse.class);

            // Then
            assertEquals(200, responseEntity.getStatusCode().value());

            response = responseEntity.getBody();
            assertNotNull(response);
            assertNotNull(response.getId());
            assertEquals(request.getTitle(), response.getTitle());
            assertEquals(request.getDescription(), response.getDescription());
            assertNotNull(response.getDonationDate());
            assertNotNull(response.getDonationExpirationDate());
            assertEquals(request.getCommunicationMethod(),
                            response.getCommunicationMethod());
            assertEquals(request.getCategory(), response.getCategory());
            assertEquals(DonationState.PENDING, response.getStatus());
            assertEquals(request.getCityId(), response.getCity().getId());
            assertEquals(request.getRooms(), response.getRooms());
            assertEquals(request.getBathrooms(), response.getBathrooms());

            PropertyDonation propertyDonation =
                            propertyDonationStubs.get(response.getId());
            dataUtil.delete(propertyDonation);
        } finally {
            // Cleanup
            dataUtil.delete(city);
        }
    }

    @Test
    void update_withValidPropertyDonationRequest_thenValidateResponse() {
        // Given
        String url = "/api/v1/donations/property/%d";

        City city = cityStubs.insert("arabic_name", "english_name");

        PropertyDonation propertyDonation = propertyDonationStubs.insert("title",
                        "description", OffsetDateTime.now(), OffsetDateTime.now(),
                        DonationCategory.PROPERTY, DonationState.ACCEPTED,
                        CommunicationMethod.CHAT, 5, 1, 1, city, 15,
                        OffsetDateTime.now().plusHours(1),
                        OffsetDateTime.now().plusMonths(1));

        PropertyDonationUpdateRequest request = new PropertyDonationUpdateRequest();
        request.setId(0L);
        request.setTitle("title");
        request.setDescription("description");
        request.setDonationDate(OffsetDateTime.now());
        request.setDonationExpirationDate(OffsetDateTime.now());
        request.setCategory(DonationCategory.PROPERTY);
        request.setStatus(DonationState.ACCEPTED);
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setCityId(city.getId());
        request.setBathrooms(2);
        request.setKitchens(2);
        request.setRooms(8);
        request.setPeopleCapacity(20);
        request.setAvailableFrom(OffsetDateTime.now().plusHours(1));
        request.setAvailableTo(OffsetDateTime.now().plusMonths(1));

        try {
            // When
            ResponseEntity<PropertyDonationResponse> responseEntity = requestUtil.put(
                            url.formatted(propertyDonation.getId()), request, null,
                            PropertyDonationResponse.class);

            // Then
            assertEquals(200, responseEntity.getStatusCodeValue());

            PropertyDonationResponse response = responseEntity.getBody();
            assertNotNull(response);

            assertNotNull(response);
            assertNotNull(response.getId());
            assertEquals(request.getTitle(), response.getTitle());
            assertEquals(request.getDescription(), response.getDescription());
            assertNotNull(response.getDonationDate());
            assertNotNull(response.getDonationExpirationDate());
            assertEquals(request.getCommunicationMethod(),
                            response.getCommunicationMethod());
            assertEquals(request.getCategory(), response.getCategory());
            assertEquals(DonationState.ACCEPTED, response.getStatus());
            assertEquals(request.getCityId(), response.getCity().getId());
            assertEquals(request.getRooms(), response.getRooms());
            assertEquals(request.getBathrooms(), response.getBathrooms());
        } finally {
            // Cleanup
            dataUtil.delete(city, propertyDonation);
        }
    }

    @Test
    void get_thenValidateResponse() {
        // Given
        String url = "/api/v1/donations/property/%d";

        City city = cityStubs.insert("arabic_name", "english_name");
        PropertyDonation propertyDonation = propertyDonationStubs.insert("title",
                        "description", OffsetDateTime.now(), OffsetDateTime.now(),
                        DonationCategory.PROPERTY, DonationState.ACCEPTED,
                        CommunicationMethod.CHAT, 5, 1, 1, city, 15,
                        OffsetDateTime.now().plusHours(1),
                        OffsetDateTime.now().plusMonths(1));
        try {
            // When
            ResponseEntity<PropertyDonationResponse> responseEntity =
                            requestUtil.get(url.formatted(propertyDonation.getId()), null,
                                            null, PropertyDonationResponse.class);

            // Then
            assertEquals(200, responseEntity.getStatusCodeValue());

            PropertyDonationResponse response = responseEntity.getBody();
            assertNotNull(response);

            assertNotNull(response);
            assertNotNull(response.getId());
            assertEquals(propertyDonation.getTitle(), response.getTitle());
            assertEquals(propertyDonation.getDescription(), response.getDescription());
            assertNotNull(response.getDonationDate());
            assertNotNull(response.getDonationExpirationDate());
            assertEquals(propertyDonation.getCommunicationMethod(),
                            response.getCommunicationMethod());
            assertEquals(propertyDonation.getCategory(), response.getCategory());
            assertEquals(DonationState.ACCEPTED, response.getStatus());
            assertEquals(propertyDonation.getCity().getId(), response.getCity().getId());
            assertEquals(propertyDonation.getRooms(), response.getRooms());
            assertEquals(propertyDonation.getBathrooms(), response.getBathrooms());
        } finally {
            // Cleanup
            dataUtil.delete(city, propertyDonation);
        }
    }

    @Test
    void list_thenValidateResponse() {
        // Given
        String url = "/api/v1/donations/property";

        City city = cityStubs.insert("arabic_name", "english_name");

        PropertyDonation propertyDonation1 = propertyDonationStubs.insert("title",
                        "description", OffsetDateTime.now(), OffsetDateTime.now(),
                        DonationCategory.PROPERTY, DonationState.ACCEPTED,
                        CommunicationMethod.CHAT, 5, 1, 1, city, 15,
                        OffsetDateTime.now().plusHours(1),
                        OffsetDateTime.now().plusMonths(1));

        PropertyDonation propertyDonation2 = propertyDonationStubs.insert("title",
                        "description", OffsetDateTime.now(), OffsetDateTime.now(),
                        DonationCategory.PROPERTY, DonationState.ACCEPTED,
                        CommunicationMethod.CHAT, 5, 1, 1, city, 15,
                        OffsetDateTime.now().plusHours(1),
                        OffsetDateTime.now().plusMonths(1));

        PropertyDonation propertyDonation3 = propertyDonationStubs.insert("title",
                        "description", OffsetDateTime.now(), OffsetDateTime.now(),
                        DonationCategory.PROPERTY, DonationState.ACCEPTED,
                        CommunicationMethod.CHAT, 5, 1, 1, city, 15,
                        OffsetDateTime.now().plusHours(1),
                        OffsetDateTime.now().plusMonths(1));

        try {
            // When
            ResponseEntity<PropertyDonationResponse[]> responseEntity = requestUtil
                            .get(url, null, null, PropertyDonationResponse[].class);

            // Then
            assertEquals(200, responseEntity.getStatusCode().value());

            PropertyDonationResponse[] response = responseEntity.getBody();
            assertNotNull(response);
            assertEquals(3, response.length);
        } finally {
            // Cleanup
            dataUtil.delete(city, propertyDonation1, propertyDonation2,
                            propertyDonation3);
        }
    }
}
