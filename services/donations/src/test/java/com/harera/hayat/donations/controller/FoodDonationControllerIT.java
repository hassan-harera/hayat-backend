package com.harera.hayat.donations.controller;

import com.harera.hayat.donations.ApplicationIT;
import com.harera.hayat.donations.model.CommunicationMethod;
import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.DonationStatus;
import com.harera.hayat.donations.model.food.*;
import com.harera.hayat.donations.stubs.city.CityStubs;
import com.harera.hayat.donations.util.DataUtil;
import com.harera.hayat.donations.util.RequestUtil;
import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.donations.stubs.food.FoodDonationStubs;
import com.harera.hayat.donations.stubs.food.FoodUnitStubs;
import com.harera.hayat.framework.model.food.FoodUnit;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
        request.setQuantity(2F);
        request.setCityId(city.getId());
        request.setFoodUnitId(foodUnit.getId());
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(LocalDate.now().plusMonths(1));

        ResponseEntity<FoodDonationResponse> responseEntity = null;
        FoodDonationResponse response = null;
        try {
            // When
            responseEntity = requestUtil.post(url, request, null,
                            FoodDonationResponse.class);

            // Then
            Assertions.assertEquals(200, responseEntity.getStatusCode().value());

            response = responseEntity.getBody();
            Assertions.assertNotNull(response);

            Assertions.assertNotNull(response.getId());
            Assertions.assertEquals(request.getTitle(), response.getTitle());
            Assertions.assertEquals(request.getDescription(), response.getDescription());
            Assertions.assertEquals(request.getQuantity(), response.getQuantity());
            Assertions.assertEquals(request.getCommunicationMethod(),
                            response.getCommunicationMethod());
            Assertions.assertEquals(request.getCityId(), response.getCity().getId());
            Assertions.assertEquals(request.getFoodUnitId(), response.getFoodUnit().getId());
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
                        LocalDateTime.now(), "title", DonationCategory.FOOD,
                        "description", city, DonationStatus.PENDING);

        FoodDonationUpdateRequest request = new FoodDonationUpdateRequest();
        request.setCityId(city.getId());
        request.setFoodUnitId(foodUnit.getId());
        request.setQuantity(2F);
        request.setTitle("new_title");
        request.setDescription("new_desc");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(LocalDate.now().plusMonths(1));

        try {
            // When
            ResponseEntity<FoodDonationResponse> responseEntity =
                            requestUtil.put(url.formatted(foodDonation.getId()), request,
                                            null, FoodDonationResponse.class);

            // Then
            Assertions.assertEquals(200, responseEntity.getStatusCodeValue());

            FoodDonationResponse response = responseEntity.getBody();
            Assertions.assertNotNull(response);

            Assertions.assertEquals(request.getTitle(), response.getTitle());
            Assertions.assertEquals(request.getDescription(), response.getDescription());
            Assertions.assertEquals(request.getQuantity(), response.getQuantity());
            Assertions.assertEquals(request.getCommunicationMethod(),
                            response.getCommunicationMethod());
            Assertions.assertEquals(request.getCityId(), response.getCity().getId());
            Assertions.assertEquals(request.getFoodUnitId(), response.getFoodUnit().getId());
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
                        LocalDateTime.now(), "title", DonationCategory.FOOD,
                        "description", city, DonationStatus.PENDING);

        try {
            // When
            ResponseEntity<FoodDonationResponse> responseEntity =
                            requestUtil.get(url.formatted(foodDonation.getId()), null,
                                            null, FoodDonationResponse.class);

            // Then
            Assertions.assertEquals(200, responseEntity.getStatusCodeValue());

            FoodDonationResponse response = responseEntity.getBody();
            Assertions.assertNotNull(response);

            Assertions.assertEquals(foodDonation.getTitle(), response.getTitle());
            Assertions.assertEquals(foodDonation.getDescription(), response.getDescription());
            Assertions.assertEquals(foodDonation.getCommunicationMethod(),
                            response.getCommunicationMethod());
            Assertions.assertEquals(foodDonation.getCity().getId(),
                            response.getCity().getId());
            Assertions.assertEquals(foodDonation.getQuantity(), response.getQuantity());
            Assertions.assertEquals(foodDonation.getFoodUnit().getId(),
                            response.getFoodUnit().getId());
            assertTrue(foodDonation.getFoodExpirationDate().toLocalDate()
                            .isEqual(response.getFoodExpirationDate().toLocalDate()));
        } finally {
            // Cleanup
            dataUtil.delete(city, foodUnit, foodDonation);
        }
    }
}
