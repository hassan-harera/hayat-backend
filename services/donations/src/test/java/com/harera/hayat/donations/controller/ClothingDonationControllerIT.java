package com.harera.hayat.donations.controller;

import com.harera.hayat.donations.ApplicationIT;
import com.harera.hayat.donations.model.CommunicationMethod;
import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.DonationState;
import com.harera.hayat.donations.model.clothing.ClothingCondition;
import com.harera.hayat.donations.model.clothing.ClothingDonation;
import com.harera.hayat.donations.model.clothing.ClothingDonationRequest;
import com.harera.hayat.donations.model.clothing.ClothingDonationResponse;
import com.harera.hayat.donations.stubs.city.CityStubs;
import com.harera.hayat.donations.stubs.clothing.ClothingConditionStubs;
import com.harera.hayat.donations.stubs.clothing.ClothingDonationStubs;
import com.harera.hayat.donations.util.DataUtil;
import com.harera.hayat.donations.util.RequestUtil;
import com.harera.hayat.framework.model.city.City;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ClothingDonationControllerIT extends ApplicationIT {

    private final RequestUtil requestUtil;
    private final CityStubs cityStubs;
    private final DataUtil dataUtil;
    private final ClothingDonationStubs clothingDonationStubs;
    private final ClothingConditionStubs clothingConditionStubs;

    @Test
    void create_withValidClothingDonationRequest_thenValidateResponse() {
        // Given
        String url = "/api/v1/donations/clothing";

        ClothingCondition condition =
                        clothingConditionStubs.insert("arabic_name", "english_name");
        City city = cityStubs.insert("arabic_name", "english_name");

        ClothingDonationRequest request = new ClothingDonationRequest();
        request.setId(0L);
        request.setTitle("title");
        request.setDescription("description");
        request.setDonationDate(OffsetDateTime.now());
        request.setDonationExpirationDate(OffsetDateTime.now());
        request.setCategory(DonationCategory.PROPERTY);
        request.setStatus(DonationState.ACCEPTED);
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setCityId(city.getId());
        request.setQuantity(5);
        request.setClothingConditionId(condition.getId());

        try {
            // When
            ResponseEntity<ClothingDonationResponse> responseEntity = requestUtil
                            .post(url, request, null, ClothingDonationResponse.class);

            // Then
            Assert.assertEquals(200, responseEntity.getStatusCode().value());

            ClothingDonationResponse response = responseEntity.getBody();
            Assert.assertNotNull(response);
            Assert.assertNotNull(response.getId());
            Assert.assertEquals(request.getTitle(), response.getTitle());
            Assert.assertEquals(request.getDescription(), response.getDescription());
            Assert.assertEquals(request.getCommunicationMethod(),
                            response.getCommunicationMethod());
            Assert.assertEquals(request.getCityId(), response.getCity().getId());

            ClothingDonation clothingDonation =
                            clothingDonationStubs.get(response.getId());
            dataUtil.delete(clothingDonation);
        } finally {
            // Cleanup
            dataUtil.delete(city, condition);
        }
    }

    @Test
    void update_withValidClothingDonationRequest_thenValidateResponse() {
        // Given
        String url = "/api/v1/donations/clothing/%d";

        City city = cityStubs.insert("arabic_name", "english_name");
        ClothingCondition condition =
                        clothingConditionStubs.insert("arabic_name", "english_name");
        ClothingDonation clothingDonation = clothingDonationStubs.insert("title",
                        "description", OffsetDateTime.now(), OffsetDateTime.now(),
                        DonationCategory.PROPERTY, DonationState.ACCEPTED,
                        CommunicationMethod.CHAT, 5, condition, null, null, null, city);
        ClothingDonationRequest request = new ClothingDonationRequest();
        request.setId(0L);
        request.setTitle("title");
        request.setDescription("description");
        request.setDonationDate(OffsetDateTime.now());
        request.setDonationExpirationDate(OffsetDateTime.now());
        request.setCategory(DonationCategory.PROPERTY);
        request.setStatus(DonationState.ACCEPTED);
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setCityId(city.getId());
        request.setQuantity(8);
        request.setClothingConditionId(condition.getId());

        try {
            // When
            ResponseEntity<ClothingDonationResponse> responseEntity = requestUtil.put(
                            url.formatted(clothingDonation.getId()), request, null,
                            ClothingDonationResponse.class);

            // Then
            Assert.assertEquals(200, responseEntity.getStatusCodeValue());

            ClothingDonationResponse response = responseEntity.getBody();
            Assert.assertNotNull(response);

            Assert.assertEquals(request.getTitle(), response.getTitle());
            Assert.assertEquals(request.getDescription(), response.getDescription());
            Assert.assertEquals(request.getQuantity(), response.getQuantity());
            Assert.assertEquals(request.getCommunicationMethod(),
                            response.getCommunicationMethod());
            Assert.assertEquals(request.getCityId(), response.getCity().getId());
        } finally {
            // Cleanup
            dataUtil.delete(city, condition, clothingDonation);
        }
    }

    @Test
    void get_thenValidateResponse() {
        // Given
        String url = "/api/v1/donations/clothing/%d";

        City city = cityStubs.insert("arabic_name", "english_name");
        ClothingCondition condition =
                        clothingConditionStubs.insert("arabic_name", "english_name");
        ClothingDonation clothingDonation = clothingDonationStubs.insert("title",
                        "description", OffsetDateTime.now(), OffsetDateTime.now(),
                        DonationCategory.PROPERTY, DonationState.ACCEPTED,
                        CommunicationMethod.CHAT, 5, condition, null, null, null, city);

        try {
            // When
            ResponseEntity<ClothingDonationResponse> responseEntity =
                            requestUtil.get(url.formatted(clothingDonation.getId()), null,
                                            null, ClothingDonationResponse.class);

            // Then
            Assert.assertEquals(200, responseEntity.getStatusCodeValue());

            ClothingDonationResponse response = responseEntity.getBody();
            Assert.assertNotNull(response);

            Assert.assertEquals(clothingDonation.getTitle(), response.getTitle());
            Assert.assertEquals(clothingDonation.getDescription(),
                            response.getDescription());
            Assert.assertEquals(clothingDonation.getCommunicationMethod(),
                            response.getCommunicationMethod());
            Assert.assertEquals(clothingDonation.getCity().getId(),
                            response.getCity().getId());
            Assertions.assertEquals(clothingDonation.getQuantity(),
                            response.getQuantity());
        } finally {
            // Cleanup
            dataUtil.delete(city, condition, clothingDonation);
        }
    }
}
