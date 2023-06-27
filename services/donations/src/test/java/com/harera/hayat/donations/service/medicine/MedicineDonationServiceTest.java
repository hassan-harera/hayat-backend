package com.harera.hayat.donations.service.medicine;

import com.harera.hayat.donations.model.CommunicationMethod;
import com.harera.hayat.donations.model.medicine.MedicineDonationRequest;
import com.harera.hayat.donations.model.medicine.MedicineDonationResponse;
import com.harera.hayat.donations.repository.medicine.MedicineDonationRepository;
import com.harera.hayat.framework.config.NotNullableMapper;
import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.model.medicine.Medicine;
import com.harera.hayat.framework.model.medicine.MedicineUnit;
import com.harera.hayat.framework.repository.city.CityRepository;
import com.harera.hayat.framework.repository.repository.MedicineRepository;
import com.harera.hayat.framework.repository.repository.MedicineUnitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicineDonationServiceTest {

    private MedicineDonationService medicineDonationService;

    @Mock
    private CityRepository cityRepository;
    @Mock
    private MedicineUnitRepository medicineUnitRepository;
    @Mock
    private MedicineDonationRepository medicineDonationRepository;
    @Mock
    private MedicineRepository medicineRepository;;
    @Mock
    private MedicineDonationValidation medicineDonationValidation;

    @BeforeEach
    void setUp() {
        medicineDonationService = new MedicineDonationService(medicineDonationValidation,
                        cityRepository, medicineUnitRepository, new NotNullableMapper(),
                        medicineDonationRepository, medicineRepository, cloudFileService, securityContext, predictionService);
    }

    @Test
    void create_withValidRequest_thenVerifyPersistenceCall() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(LocalDateTime.now());
        request.setQuantity(1F);
        request.setMedicineUnitId(1L);
        request.setMedicineId(1L);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));

        City city = new City();
        city.setId(1L);

        Medicine medicine = new Medicine();
        medicine.setId(1L);

        MedicineUnit medicineUnit = new MedicineUnit();
        medicineUnit.setId(1L);

        // when
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(medicineRepository.findById(1L)).thenReturn(Optional.of(medicine));
        when(medicineUnitRepository.findById(1L)).thenReturn(Optional.of(medicineUnit));

        MedicineDonationResponse response = medicineDonationService.create(request, authorization.substring(7));

        // then
        medicineDonationRepository.save(any());

        assertEquals(request.getTitle(), response.getTitle());
        assertEquals(request.getDescription(), response.getDescription());
    }

    @Test
    void create_withNotFoundUnit_thenEntityNotFoundException() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(LocalDateTime.now());
        request.setMedicineId(1L);
        request.setMedicineUnitId(1L);
        request.setQuantity(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));

        // when
        when(cityRepository.findById(1L)).thenReturn(Optional.of(new City()));
        when(medicineUnitRepository.findById(1L)).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class,
                        () -> medicineDonationService.create(request, authorization.substring(7)));
    }

    @Test
    void create_withNotFoundMedicine_thenEntityNotFoundException() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(LocalDateTime.now());
        request.setMedicineId(1L);
        request.setMedicineUnitId(1L);
        request.setQuantity(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));

        // when
        when(cityRepository.findById(1L)).thenReturn(Optional.of(new City()));
        when(medicineRepository.findById(1L)).thenReturn(Optional.empty());
        when(medicineUnitRepository.findById(1L))
                        .thenReturn(Optional.of(new MedicineUnit()));

        Exception ex = null;
        try {
            medicineDonationService.create(request, authorization.substring(7));
        } catch (Exception e) {
            ex = e;

        }

        // then
        assertNotNull(ex);
        assertEquals(EntityNotFoundException.class, ex.getClass());
    }

    @Test
    void create_withNotFoundCity_thenEntityNotFoundException() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setDonationDate(LocalDateTime.now());
        request.setMedicineId(1L);
        request.setMedicineUnitId(1L);
        request.setQuantity(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));

        // when
        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        Exception ex = null;
        try {
            medicineDonationService.create(request, authorization.substring(7));
        } catch (Exception e) {
            ex = e;

        }

        // then
        assertNotNull(ex);
        assertEquals(EntityNotFoundException.class, ex.getClass());
    }

    @Test
    void create_withValidRequest_thenVerifyMapping() {
        // given
        MedicineDonationRequest request = new MedicineDonationRequest();
        request.setCityId(1L);
        request.setMedicineId(1L);
        request.setMedicineUnitId(1L);
        request.setQuantity(1F);
        request.setTitle("title");
        request.setDescription("description");
        request.setCommunicationMethod(CommunicationMethod.CHAT);
        request.setMedicineExpirationDate(LocalDateTime.now().plusMonths(1));
        request.setDonationExpirationDate(LocalDateTime.now().plusMonths(1));

        // when
        when(cityRepository.findById(1L)).thenReturn(Optional.of(new City()));
        when(medicineRepository.findById(1L)).thenReturn(Optional.of(new Medicine()));
        when(medicineUnitRepository.findById(1L))
                        .thenReturn(Optional.of(new MedicineUnit()));

        MedicineDonationResponse medicineDonationResponse =
                        medicineDonationService.create(request, authorization.substring(7));
        // then
        assertEquals(request.getTitle(), medicineDonationResponse.getTitle());
        assertEquals(request.getDescription(), medicineDonationResponse.getDescription());
        assertEquals(request.getCommunicationMethod(),
                        medicineDonationResponse.getCommunicationMethod());
        assertEquals(request.getMedicineExpirationDate(),
                        medicineDonationResponse.getMedicineExpirationDate());
        assertEquals(request.getMedicineExpirationDate(),
                        medicineDonationResponse.getMedicineExpirationDate());
        assertEquals(request.getQuantity(), medicineDonationResponse.getQuantity());

        verify(medicineDonationRepository, times(1)).save(any());
    }
}
