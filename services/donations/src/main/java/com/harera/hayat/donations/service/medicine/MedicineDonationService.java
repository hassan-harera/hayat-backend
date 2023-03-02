package com.harera.hayat.donations.service.medicine;

import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.DonationState;
import com.harera.hayat.donations.model.medicine.MedicineDonation;
import com.harera.hayat.donations.model.medicine.MedicineDonationRequest;
import com.harera.hayat.donations.model.medicine.MedicineDonationResponse;
import com.harera.hayat.donations.model.medicine.MedicineDonationUpdateRequest;
import com.harera.hayat.donations.repository.medicine.MedicineDonationRepository;
import com.harera.hayat.donations.service.BaseService;
import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.model.medicine.Medicine;
import com.harera.hayat.framework.model.medicine.MedicineUnit;
import com.harera.hayat.framework.repository.city.CityRepository;
import com.harera.hayat.framework.repository.repository.MedicineRepository;
import com.harera.hayat.framework.repository.repository.MedicineUnitRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class MedicineDonationService implements BaseService {

    private final MedicineDonationValidation donationValidation;
    private final CityRepository cityRepository;
    private final MedicineUnitRepository medicineUnitRepository;
    private final ModelMapper modelMapper;
    private final MedicineDonationRepository medicineDonationRepository;
    private final MedicineRepository medicineRepository;

    public MedicineDonationService(MedicineDonationValidation donationValidation,
                    CityRepository cityRepository,
                    MedicineUnitRepository medicineUnitRepository,
                    ModelMapper modelMapper,
                    MedicineDonationRepository medicineDonationRepository,
                    MedicineRepository medicineRepository) {
        this.donationValidation = donationValidation;
        this.cityRepository = cityRepository;
        this.medicineUnitRepository = medicineUnitRepository;
        this.modelMapper = modelMapper;
        this.medicineDonationRepository = medicineDonationRepository;
        this.medicineRepository = medicineRepository;
    }

    public MedicineDonationResponse create(
                    MedicineDonationRequest medicineDonationRequest) {
        donationValidation.validateCreate(medicineDonationRequest);

        MedicineDonation medicineDonation =
                        modelMapper.map(medicineDonationRequest, MedicineDonation.class);
        medicineDonation.setCategory(DonationCategory.MEDICINE);
        medicineDonation.setCity(getCity(medicineDonationRequest.getCityId()));
        medicineDonation.setDonationDate(OffsetDateTime.now());
        // TODO: 28/02/23 get user from token
        medicineDonation.setMedicineUnit(
                        getUnit(medicineDonationRequest.getMedicineUnitId()));
        medicineDonation.setMedicine(
                        getMedicine(medicineDonationRequest.getMedicineId()));
        medicineDonationRepository.save(medicineDonation);
        return modelMapper.map(medicineDonation, MedicineDonationResponse.class);
    }

    private Medicine getMedicine(long medicineId) {
        return medicineRepository.findById(medicineId).orElseThrow(
                        () -> new EntityNotFoundException(Medicine.class, medicineId));
    }

    private MedicineUnit getUnit(long unitId) {
        return medicineUnitRepository.findById(unitId).orElseThrow(
                        () -> new EntityNotFoundException(MedicineUnit.class, unitId));
    }

    private City getCity(long cityId) {
        return cityRepository.findById(cityId).orElseThrow(
                        () -> new EntityNotFoundException(City.class, cityId));
    }

    public MedicineDonationResponse get(Long id) {
        return modelMapper.map(
                        medicineDonationRepository.findById(id)
                                        .orElseThrow(() -> new EntityNotFoundException(
                                                        MedicineDonation.class, id)),
                        MedicineDonationResponse.class);
    }

    public List<MedicineDonationResponse> list(int size, int page) {
        return medicineDonationRepository.findAll(Pageable.ofSize(size).withPage(page))
                        .map(medicineDonation -> modelMapper.map(medicineDonation,
                                        MedicineDonationResponse.class))
                        .toList();
    }

    public MedicineDonationResponse update(Long id,
                    MedicineDonationUpdateRequest request) {
        donationValidation.validateUpdate(id, request);

        MedicineDonation medicineDonation = medicineDonationRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                        MedicineDonation.class, id));
        modelMapper.map(request, medicineDonation);

        medicineDonation.setCategory(DonationCategory.MEDICINE);
        // TODO: 28/02/23 send request to ai model
        medicineDonation.setStatus(DonationState.PENDING);

        // TODO: 28/02/23 get user from token
        medicineDonation.setCity(getCity(request.getCityId()));

        medicineDonation.setMedicineUnit(getUnit(request.getMedicineUnitId()));
        medicineDonation.setMedicine(getMedicine(request.getMedicineId()));

        medicineDonationRepository.save(medicineDonation);
        return modelMapper.map(medicineDonation, MedicineDonationResponse.class);

    }
}
