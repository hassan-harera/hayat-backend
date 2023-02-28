package com.harera.hayat.donations.controller;

import com.harera.hayat.donations.ApplicationIT;
import com.harera.hayat.donations.model.CommunicationMethod;
import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.DonationState;
import com.harera.hayat.donations.model.food.*;
import com.harera.hayat.donations.stubs.city.CityStubs;
import com.harera.hayat.donations.util.DataUtil;
import com.harera.hayat.donations.util.RequestUtil;
import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.donations.stubs.food.FoodDonationStubs;
import com.harera.hayat.donations.stubs.food.FoodUnitStubs;
import com.harera.hayat.framework.model.food.FoodCategory;
import com.harera.hayat.framework.model.food.FoodUnit;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;

import static org.junit.Assert.assertTrue;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class FoodDonationControllerIT extends ApplicationIT {

    private final RequestUtil requestUtil;
    private final CityStubs cityStubs;
    private final DataUtil dataUtil;
    private final FoodUnitStubs foodUnitStubs;
    private final FoodDonationStubs foodDonationStubs;

    @Test
    void create_withValidFoodDonationRequest_thenValidateResponse() {
        // Given
        String url = "/api/v1/donations/food";

        City city = cityStubs.insert("arabic_name", "english_name");
        FoodUnit foodUnit =
                        foodUnitStubs.insert("foodUnitArabicName", "foodUnitEnglishName");

        FoodDonationRequest request = new FoodDonationRequest();
        request.setTitle("title");
        request.setDescription("description");
        request.setAmount(2F);
        request.setCityId(city.getId());
        request.setUnitId(foodUnit.getId());
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));

        ResponseEntity<FoodDonationResponse> responseEntity = null;
        FoodDonationResponse response = null;
        try {
            // When
            responseEntity = requestUtil.post(url, request, null,
                            FoodDonationResponse.class);

            // Then
            Assert.assertEquals(200, responseEntity.getStatusCode().value());

            response = responseEntity.getBody();
            Assert.assertNotNull(response);

            Assert.assertNotNull(response.getId());
            Assert.assertEquals(request.getTitle(), response.getTitle());
            Assert.assertEquals(request.getDescription(), response.getDescription());
            Assert.assertEquals(request.getAmount(), response.getAmount());
            Assert.assertEquals(request.getCommunicationMethod(),
                            response.getCommunicationMethod());
            Assert.assertEquals(request.getCityId(), response.getCity().getId());
            Assert.assertEquals(request.getUnitId(), response.getUnit().getId());
            assertTrue(request.getFoodExpirationDate()
                            .isEqual(response.getFoodExpirationDate()));

            FoodDonation foodDonation = foodDonationStubs.get(response.getId());
            dataUtil.delete(foodDonation);
        } finally {
            // Cleanup
            dataUtil.delete(city, foodUnit);
        }
    }

    @Test
    void update_withValidFoodDonationRequest_thenValidateResponse() {
        // Given
        String url = "/api/v1/donations/food/%d";

        City city = cityStubs.insert("arabic_name", "english_name");
        FoodUnit foodUnit =
                        foodUnitStubs.insert("foodUnitArabicName", "foodUnitEnglishName");

        FoodDonation foodDonation = foodDonationStubs.insert(foodUnit, 1F,
                        OffsetDateTime.now(), "title", DonationCategory.FOOD,
                        "description", city, DonationState.PENDING);

        FoodDonationUpdateRequest request = new FoodDonationUpdateRequest();
        request.setCityId(city.getId());
        request.setUnitId(foodUnit.getId());
        request.setAmount(2F);
        request.setTitle("new_title");
        request.setDescription("new_desc");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));

        try {
            // When
            ResponseEntity<FoodDonationResponse> responseEntity =
                            requestUtil.put(url.formatted(foodDonation.getId()), request,
                                            null, FoodDonationResponse.class);

            // Then
            Assert.assertEquals(200, responseEntity.getStatusCodeValue());

            FoodDonationResponse response = responseEntity.getBody();
            Assert.assertNotNull(response);

            Assert.assertEquals(request.getTitle(), response.getTitle());
            Assert.assertEquals(request.getDescription(), response.getDescription());
            Assert.assertEquals(request.getAmount(), response.getAmount());
            Assert.assertEquals(request.getCommunicationMethod(),
                            response.getCommunicationMethod());
            Assert.assertEquals(request.getCityId(), response.getCity().getId());
            Assert.assertEquals(request.getUnitId(), response.getUnit().getId());
            assertTrue(request.getFoodExpirationDate()
                            .isEqual(response.getFoodExpirationDate()));
        } finally {
            // Cleanup
            dataUtil.delete(city, foodUnit, foodDonation);
        }
    }

    @Test
    void get_thenValidateResponse() {
        // Given
        String url = "/api/v1/donations/food/%d";

        City city = cityStubs.insert("arabic_name", "english_name");
        FoodUnit foodUnit =
                        foodUnitStubs.insert("foodUnitArabicName", "foodUnitEnglishName");

        FoodDonation foodDonation = foodDonationStubs.insert(foodUnit, 1F,
                        OffsetDateTime.now(), "title", DonationCategory.FOOD,
                        "description", city, DonationState.PENDING);

        try {
            // When
            ResponseEntity<FoodDonationResponse> responseEntity =
                            requestUtil.get(url.formatted(foodDonation.getId()), null,
                                            null, FoodDonationResponse.class);

            // Then
            Assert.assertEquals(200, responseEntity.getStatusCodeValue());

            FoodDonationResponse response = responseEntity.getBody();
            Assert.assertNotNull(response);

            Assert.assertEquals(foodDonation.getTitle(), response.getTitle());
            Assert.assertEquals(foodDonation.getDescription(), response.getDescription());
            Assert.assertEquals(foodDonation.getCommunicationMethod(),
                            response.getCommunicationMethod());
            Assert.assertEquals(foodDonation.getCity().getId(),
                            response.getCity().getId());
            Assert.assertEquals(foodDonation.getAmount(), response.getAmount());
            Assert.assertEquals(foodDonation.getUnit().getId(),
                            response.getUnit().getId());
            assertTrue(foodDonation.getFoodExpirationDate().toLocalDate()
                            .isEqual(response.getFoodExpirationDate().toLocalDate()));
        } finally {
            // Cleanup
            dataUtil.delete(city, foodUnit, foodDonation);
        }
    }
}
