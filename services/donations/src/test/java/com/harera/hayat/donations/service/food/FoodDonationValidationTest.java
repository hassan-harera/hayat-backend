package com.harera.hayat.donations.service.food;

import com.harera.hayat.donations.model.CommunicationMethod;
import com.harera.hayat.donations.model.food.FoodDonationRequest;
import com.harera.hayat.donations.model.food.FoodDonationUpdateRequest;
import com.harera.hayat.donations.repository.food.FoodDonationRepository;
import com.harera.hayat.donations.service.DonationValidation;
import com.harera.hayat.framework.exception.FieldFormatException;
import com.harera.hayat.framework.exception.MandatoryFieldException;
import com.harera.hayat.framework.util.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FoodDonationValidationTest {

    @Mock
    private DonationValidation donationValidation;
    @Mock
    private FoodDonationRepository foodDonationRepository;

    private FoodDonationValidation foodDonationValidation;

    @BeforeEach
    void setUp() {
        foodDonationValidation = new FoodDonationValidation(donationValidation,
                        foodDonationRepository);
    }

    @Test
    void validateCreate_withoutAmount_thenThrowMandatoryFieldException() {
        // given
        FoodDonationRequest request = new FoodDonationRequest();
        request.setCityId(1L);
        request.setFoodUnitId(1L);
        request.setTitle("title");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));

        // when

        MandatoryFieldException ex = assertThrows(MandatoryFieldException.class, () -> {
            foodDonationValidation.validateCreate(request);
        });

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.MANDATORY_FOOD_DONATION_AMOUNT, ex.getCode());
    }

    @Test
    void validateCreate_withoutUnit_thenThrowMandatoryFieldException() {
        // given
        FoodDonationRequest request = new FoodDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(OffsetDateTime.now());
        request.setQuantity(15f);
        request.setTitle("title");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));

        // when
        MandatoryFieldException ex = assertThrows(MandatoryFieldException.class, () -> {
            foodDonationValidation.validateCreate(request);
        });

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.MANDATORY_FOOD_DONATION_UNIT, ex.getCode());
    }

    @Test
    void validateCreate_withoutFoodExpirationDate_thenThrowMandatoryFieldException() {
        // given
        FoodDonationRequest request = new FoodDonationRequest();
        request.setCityId(1L);
        request.setFoodUnitId(1L);
        request.setQuantity(15f);
        request.setTitle("title");
        request.setCommunicationMethod(CommunicationMethod.CHAT);

        // when
        MandatoryFieldException ex = assertThrows(MandatoryFieldException.class, () -> {
            foodDonationValidation.validateCreate(request);
        });

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.MANDATORY_FOOD_DONATION_FOOD_EXPIRATION_DATE,
                        ex.getCode());
    }

    @Test
    void validateCreate_withInvalidAmountFormat_thenThrowFormatFieldException() {
        // given
        FoodDonationRequest request = new FoodDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(OffsetDateTime.now());

        request.setFoodUnitId(1L);
        request.setQuantity(-1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));

        // when
        FieldFormatException ex = assertThrows(FieldFormatException.class, () -> {
            foodDonationValidation.validateCreate(request);
        });

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.FORMAT_FOOD_DONATION_AMOUNT, ex.getCode());
    }

    @Test
    void validateUpdate_withoutAmount_thenThrowMandatoryFieldException() {
        // given
        Long id = 1L;

        FoodDonationUpdateRequest request = new FoodDonationUpdateRequest();
        request.setCityId(1L);
        request.setFoodUnitId(1L);
        request.setTitle("title");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));

        // when

        MandatoryFieldException ex = assertThrows(MandatoryFieldException.class, () -> {
            foodDonationValidation.validateUpdate(id, request);
        });

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.MANDATORY_FOOD_DONATION_AMOUNT, ex.getCode());
    }

    @Test
    void validateUpdate_withoutUnit_thenThrowMandatoryFieldException() {
        // given
        Long id = 1L;
        FoodDonationUpdateRequest request = new FoodDonationUpdateRequest();

        request.setCityId(1L);
        request.setDonationDate(OffsetDateTime.now());
        request.setQuantity(15f);
        request.setTitle("title");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));

        // when
        MandatoryFieldException ex = assertThrows(MandatoryFieldException.class, () -> {
            foodDonationValidation.validateUpdate(id, request);
        });

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.MANDATORY_FOOD_DONATION_UNIT, ex.getCode());
    }

    @Test
    void validateUpdate_withoutFoodExpirationDate_thenThrowMandatoryFieldException() {
        // given
        Long id = 1L;

        FoodDonationUpdateRequest request = new FoodDonationUpdateRequest();
        request.setCityId(1L);
        request.setFoodUnitId(1L);
        request.setQuantity(15f);
        request.setTitle("title");
        request.setCommunicationMethod(CommunicationMethod.CHAT);

        // when
        MandatoryFieldException ex = assertThrows(MandatoryFieldException.class, () -> {
            foodDonationValidation.validateUpdate(id, request);
        });

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.MANDATORY_FOOD_DONATION_FOOD_EXPIRATION_DATE,
                        ex.getCode());
    }

    @Test
    void validateUpdate_withInvalidAmountFormat_thenThrowFormatFieldException() {
        // given
        FoodDonationUpdateRequest request = new FoodDonationUpdateRequest();
        request.setCityId(1L);
        request.setDonationDate(OffsetDateTime.now());
        request.setFoodUnitId(1L);
        request.setQuantity(-1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));
        request.setFoodExpirationDate(OffsetDateTime.now().plusMonths(1));

        // when
        FieldFormatException ex = assertThrows(FieldFormatException.class, () -> {
            foodDonationValidation.validateUpdate(1L, request);
        });

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.FORMAT_FOOD_DONATION_AMOUNT, ex.getCode());
    }
}
