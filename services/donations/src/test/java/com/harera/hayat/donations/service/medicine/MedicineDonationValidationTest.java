package com.harera.hayat.donations.service.medicine;

import com.harera.hayat.donations.model.CommunicationMethod;
import com.harera.hayat.donations.model.medicine.MedicineDonationRequest;
import com.harera.hayat.donations.model.medicine.MedicineDonationUpdateRequest;
import com.harera.hayat.donations.repository.medicine.MedicineDonationRepository;
import com.harera.hayat.donations.service.DonationValidation;
import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.exception.FieldLimitException;
import com.harera.hayat.framework.exception.MandatoryFieldException;
import com.harera.hayat.framework.repository.city.CityRepository;
import com.harera.hayat.framework.repository.repository.MedicineRepository;
import com.harera.hayat.framework.repository.repository.MedicineUnitRepository;
import com.harera.hayat.framework.util.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicineDonationValidationTest {

    @Mock
    private DonationValidation donationValidation;
    @Mock
    private MedicineDonationRepository medicineDonationRepository;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private MedicineRepository medicineRepository;
    @Mock
    private MedicineUnitRepository medicineUnitRepository;

    private MedicineDonationValidation medicineDonationValidation;

    @BeforeEach
    void setUp() {
        medicineDonationValidation = new MedicineDonationValidation(donationValidation,
                        medicineDonationRepository, cityRepository, medicineRepository,
                        medicineUnitRepository);
    }

    @Test
    void validateCreate_withoutAmount_thenThrowMandatoryFieldException() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setMedicineUnitId(1L);
        request.setTitle("title");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));

        // when
        MandatoryFieldException ex = assertThrows(MandatoryFieldException.class, () -> {
            medicineDonationValidation.validateCreate(request);
        });

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.MANDATORY_MEDICINE_DONATION_AMOUNT, ex.getCode());
        Mockito.verify(donationValidation, Mockito.atLeastOnce()).validateCreate(request);
    }

    @Test
    void validateCreate_withoutUnitId_thenThrowMandatoryFieldException() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(LocalDateTime.now());
        request.setQuantity(15f);
        request.setTitle("title");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));

        // when
        MandatoryFieldException ex = assertThrows(MandatoryFieldException.class, () -> {
            medicineDonationValidation.validateCreate(request);
        });

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.MANDATORY_MEDICINE_DONATION_UNIT, ex.getCode());
    }

    @Test
    void validateCreate_withoutMedicineId_thenThrowMandatoryFieldException() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setMedicineUnitId(1L);
        request.setDonationDate(LocalDateTime.now());
        request.setQuantity(15f);
        request.setTitle("title");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));

        // when
        MandatoryFieldException ex = assertThrows(MandatoryFieldException.class, () -> {
            medicineDonationValidation.validateCreate(request);
        });

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.MANDATORY_MEDICINE_DONATION_MEDICINE, ex.getCode());
    }

    @Test
    void validateCreate_withoutMedicineExpirationDate_thenThrowMandatoryFieldException() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setMedicineUnitId(1L);
        request.setMedicineId(1L);
        request.setQuantity(15f);
        request.setTitle("title");
        request.setCommunicationMethod(CommunicationMethod.CHAT);

        // when
        MandatoryFieldException ex = assertThrows(MandatoryFieldException.class, () -> {
            medicineDonationValidation.validateCreate(request);
        });

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.MANDATORY_MEDICINE_DONATION_MEDICINE_EXPIRATION_DATE,
                        ex.getCode());
    }

    @Test
    void validateCreate_withInvalidAmountFormat_thenThrowFormatFieldException() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(LocalDateTime.now());

        request.setMedicineUnitId(1L);
        request.setMedicineId(1L);
        request.setQuantity(-1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));

        // when
        FieldLimitException ex = assertThrows(FieldLimitException.class, () -> {
            medicineDonationValidation.validateCreate(request);
        });

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.FORMAT_MEDICINE_DONATION_AMOUNT, ex.getCode());
    }

    @Test
    void validateCreate_withInvalidMedicineExpirationDate_thenThrowFormatFieldException() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(LocalDateTime.now());
        request.setMedicineUnitId(1L);
        request.setMedicineId(1L);
        request.setQuantity(1f);
        request.setTitle("a");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(LocalDateTime.now().minusDays(1));

        // when
        FieldLimitException ex = assertThrows(FieldLimitException.class, () -> {
            medicineDonationValidation.validateCreate(request);
        });

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.FORMAT_MEDICINE_DONATION_EXPIRATION_DATE, ex.getCode());
    }

    @Test
    void validateCreate_withNotExistedCity_shouldThrowEntityNotFoundException() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setQuantity(1F);
        request.setMedicineId(1L);
        request.setCityId(1L);
        request.setMedicineUnitId(1L);
        request.setTitle("title");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));

        // when
        when(cityRepository.existsById(request.getCityId())).thenReturn(false);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            medicineDonationValidation.validateCreate(request);
        });

        // then
        Mockito.verify(donationValidation, Mockito.atLeastOnce()).validateCreate(request);
    }

    @Test
    void validateCreate_withNotExistedMedicine_shouldThrowEntityNotFoundException() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setQuantity(1F);
        request.setMedicineId(1L);
        request.setCityId(1L);
        request.setMedicineUnitId(1L);
        request.setTitle("title");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));

        // when
        when(cityRepository.existsById(request.getCityId())).thenReturn(true);
        when(medicineRepository.existsById(request.getMedicineId())).thenReturn(false);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            medicineDonationValidation.validateCreate(request);
        });

        // then
        Mockito.verify(donationValidation, Mockito.atLeastOnce()).validateCreate(request);
    }

    @Test
    void validateCreate_withNotExistedMedicineUnit_shouldThrowEntityNotFoundException() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setQuantity(1F);
        request.setMedicineId(1L);
        request.setCityId(1L);
        request.setMedicineUnitId(1L);
        request.setTitle("title");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));

        // when
        when(cityRepository.existsById(request.getCityId())).thenReturn(true);
        when(medicineRepository.existsById(request.getMedicineId())).thenReturn(true);
        when(medicineUnitRepository.existsById(request.getMedicineUnitId())).thenReturn(false);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            medicineDonationValidation.validateCreate(request);
        });

        // then
        Mockito.verify(donationValidation, Mockito.atLeastOnce()).validateCreate(request);
    }

    @Test
    void validateCreate_withValidateRequest_thenVerifySuperMethod() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setQuantity(1F);
        request.setMedicineId(1L);
        request.setCityId(1L);
        request.setMedicineUnitId(1L);
        request.setTitle("title");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));

        // when
        when(cityRepository.existsById(request.getCityId())).thenReturn(true);
        when(medicineRepository.existsById(request.getMedicineId())).thenReturn(true);
        when(medicineUnitRepository.existsById(request.getMedicineUnitId())).thenReturn(true);

        medicineDonationValidation.validateCreate(request);

        // then
        Mockito.verify(donationValidation, Mockito.atLeastOnce()).validateCreate(request);
    }

    @Test
    void validateUpdate_withoutAmount_thenThrowMandatoryFieldException() {
        // given
        Long id = 1L;

        MedicineDonationUpdateRequest request = new MedicineDonationUpdateRequest();
        request.setCityId(1L);
        request.setMedicineUnitId(1L);
        request.setTitle("title");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));

        // when

        MandatoryFieldException ex = assertThrows(MandatoryFieldException.class, () -> {
            medicineDonationValidation.validateUpdate(id, request);
        });

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.MANDATORY_MEDICINE_DONATION_AMOUNT, ex.getCode());
    }

    @Test
    void validateUpdate_withoutUnit_thenThrowMandatoryFieldException() {
        // given
        Long id = 1L;
        MedicineDonationUpdateRequest request = new MedicineDonationUpdateRequest();
        request.setMedicineUnitId(null);
        request.setCityId(1L);
        request.setDonationDate(LocalDateTime.now());
        request.setQuantity(15f);
        request.setTitle("title");
        request.setMedicineId(1L);
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));

        // when
        MandatoryFieldException ex = assertThrows(MandatoryFieldException.class, () -> {
            medicineDonationValidation.validateUpdate(id, request);
        });

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.MANDATORY_MEDICINE_DONATION_UNIT, ex.getCode());
    }

    @Test
    void validateUpdate_withoutMedicineExpirationDate_thenThrowMandatoryFieldException() {
        // given
        Long id = 1L;

        MedicineDonationUpdateRequest request = new MedicineDonationUpdateRequest();
        request.setCityId(1L);
        request.setMedicineUnitId(1L);
        request.setMedicineId(1L);
        request.setQuantity(15f);
        request.setTitle("title");
        request.setCommunicationMethod(CommunicationMethod.CHAT);

        // when
        MandatoryFieldException ex = assertThrows(MandatoryFieldException.class, () -> {
            medicineDonationValidation.validateUpdate(id, request);
        });

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.MANDATORY_MEDICINE_DONATION_MEDICINE_EXPIRATION_DATE,
                        ex.getCode());
    }

    @Test
    void validateUpdate_withInvalidAmountFormat_thenThrowFormatFieldException() {
        // given
        MedicineDonationUpdateRequest request = new MedicineDonationUpdateRequest();
        request.setCityId(1L);
        request.setDonationDate(LocalDateTime.now());
        request.setMedicineId(1L);
        request.setMedicineUnitId(1L);
        request.setQuantity(-1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));

        // when
        FieldLimitException ex = assertThrows(FieldLimitException.class, () -> {
            medicineDonationValidation.validateUpdate(1L, request);
        });

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.FORMAT_MEDICINE_DONATION_AMOUNT, ex.getCode());
    }
}
