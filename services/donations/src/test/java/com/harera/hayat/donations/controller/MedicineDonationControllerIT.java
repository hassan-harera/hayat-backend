package com.harera.hayat.donations.controller;

import com.harera.hayat.donations.ApplicationIT;
import com.harera.hayat.donations.model.CommunicationMethod;
import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.DonationState;
import com.harera.hayat.donations.model.medicine.MedicineDonation;
import com.harera.hayat.donations.model.medicine.MedicineDonationRequest;
import com.harera.hayat.donations.model.medicine.MedicineDonationResponse;
import com.harera.hayat.donations.stubs.city.CityStubs;
import com.harera.hayat.donations.stubs.medicine.MedicineDonationStubs;
import com.harera.hayat.donations.stubs.medicine.MedicineStubs;
import com.harera.hayat.donations.stubs.medicine.MedicineUnitStubs;
import com.harera.hayat.donations.util.DataUtil;
import com.harera.hayat.donations.util.RequestUtil;
import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.model.medicine.Medicine;
import com.harera.hayat.framework.model.medicine.MedicineUnit;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;

import static org.junit.Assert.assertTrue;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class MedicineDonationControllerIT extends ApplicationIT {

    private final RequestUtil requestUtil;
    private final CityStubs cityStubs;
    private final DataUtil dataUtil;
    private final MedicineUnitStubs medicineUnitStubs;
    private final MedicineStubs medicineStubs;
    private final MedicineDonationStubs medicineDonationStubs;

    @Test
    void create_withValidMedicineDonationRequest_thenValidateResponse() {
        // Given
        String url = "/api/v1/donations/medicine";

        City city = cityStubs.insert("arabic_name", "english_name");
        MedicineUnit medicineUnit = medicineUnitStubs.insert("medicineUnitArabicName",
                        "medicineUnitEnglishName");
        Medicine medicine = medicineStubs.insert("medicineUnitArabicName",
                        "medicineUnitEnglishName", medicineUnit);

        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setTitle("title");
        request.setDescription("description");
        request.setAmount(2F);
        request.setCityId(city.getId());
        request.setMedicineUnitId(medicineUnit.getId());
        request.setMedicineId(medicine.getId());
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(OffsetDateTime.now().plusMonths(1));

        ResponseEntity<MedicineDonationResponse> responseEntity = null;
        MedicineDonationResponse response = null;
        try {
            // When
            responseEntity = requestUtil.post(url, request, null,
                            MedicineDonationResponse.class);

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
            Assert.assertEquals(request.getMedicineUnitId(), response.getMedicineUnit().getId());
            assertTrue(request.getMedicineExpirationDate()
                            .isEqual(response.getMedicineExpirationDate()));

            MedicineDonation medicineDonation =
                            medicineDonationStubs.get(response.getId());
            dataUtil.delete(medicineDonation);
        } finally {
            // Cleanup
            dataUtil.delete(city, medicineUnit);
        }
    }

    @Test
    void update_withValidMedicineDonationRequest_thenValidateResponse() {
        // Given
        String url = "/api/v1/donations/medicine/%d";

        City city = cityStubs.insert("arabic_name", "english_name");
        MedicineUnit medicineUnit = medicineUnitStubs.insert("medicineUnitArabicName",
                        "medicineUnitEnglishName");
        Medicine medicine = medicineStubs.insert("medicineUnitArabicName",
                "medicineUnitEnglishName", medicineUnit);

        MedicineDonation medicineDonation = medicineDonationStubs.insert(medicineUnit, 1F,
                        OffsetDateTime.now(), "title", DonationCategory.FOOD,
                        "description", city, DonationState.PENDING);

        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(city.getId());
        request.setMedicineUnitId(medicineUnit.getId());
        request.setMedicineId(medicine.getId());
        request.setAmount(2F);
        request.setTitle("new_title");
        request.setDescription("new_desc");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(OffsetDateTime.now().plusMonths(1));

        try {
            // When
            ResponseEntity<MedicineDonationResponse> responseEntity = requestUtil.put(
                            url.formatted(medicineDonation.getId()), request, null,
                            MedicineDonationResponse.class);

            // Then
            Assert.assertEquals(200, responseEntity.getStatusCodeValue());

            MedicineDonationResponse response = responseEntity.getBody();
            Assert.assertNotNull(response);

            Assert.assertEquals(request.getTitle(), response.getTitle());
            Assert.assertEquals(request.getDescription(), response.getDescription());
            Assert.assertEquals(request.getAmount(), response.getAmount());
            Assert.assertEquals(request.getCommunicationMethod(),
                            response.getCommunicationMethod());
            Assert.assertEquals(request.getCityId(), response.getCity().getId());
            Assert.assertEquals(request.getMedicineUnitId(), response.getMedicineUnit().getId());
            assertTrue(request.getMedicineExpirationDate()
                            .isEqual(response.getMedicineExpirationDate()));
        } finally {
            // Cleanup
            dataUtil.delete(city, medicineUnit, medicineDonation);
        }
    }

    @Test
    void get_thenValidateResponse() {
        // Given
        String url = "/api/v1/donations/medicine/%d";

        City city = cityStubs.insert("arabic_name", "english_name");
        MedicineUnit medicineUnit = medicineUnitStubs.insert("medicineUnitArabicName",
                        "medicineUnitEnglishName");

        MedicineDonation medicineDonation = medicineDonationStubs.insert(medicineUnit, 1F,
                        OffsetDateTime.now(), "title", DonationCategory.FOOD,
                        "description", city, DonationState.PENDING);

        try {
            // When
            ResponseEntity<MedicineDonationResponse> responseEntity =
                            requestUtil.get(url.formatted(medicineDonation.getId()), null,
                                            null, MedicineDonationResponse.class);

            // Then
            Assert.assertEquals(200, responseEntity.getStatusCodeValue());

            MedicineDonationResponse response = responseEntity.getBody();
            Assert.assertNotNull(response);

            Assert.assertEquals(medicineDonation.getTitle(), response.getTitle());
            Assert.assertEquals(medicineDonation.getDescription(),
                            response.getDescription());
            Assert.assertEquals(medicineDonation.getCommunicationMethod(),
                            response.getCommunicationMethod());
            Assert.assertEquals(medicineDonation.getCity().getId(),
                            response.getCity().getId());
            Assert.assertEquals(medicineDonation.getAmount(), response.getAmount());
            Assert.assertEquals(medicineDonation.getMedicineUnit().getId(),
                            response.getMedicineUnit().getId());
            assertTrue(medicineDonation.getMedicineExpirationDate().toLocalDate()
                            .isEqual(response.getMedicineExpirationDate().toLocalDate()));
        } finally {
            // Cleanup
            dataUtil.delete(city, medicineUnit, medicineDonation);
        }
    }
}
