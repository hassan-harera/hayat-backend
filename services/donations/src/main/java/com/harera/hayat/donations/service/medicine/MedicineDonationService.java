package com.harera.hayat.donations.service.medicine;

import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.DonationStatus;
import com.harera.hayat.donations.model.ai.Prediction;
import com.harera.hayat.donations.model.medicine.MedicineDonation;
import com.harera.hayat.donations.model.medicine.MedicineDonationRequest;
import com.harera.hayat.donations.model.medicine.MedicineDonationResponse;
import com.harera.hayat.donations.model.medicine.MedicineDonationUpdateRequest;
import com.harera.hayat.donations.repository.medicine.MedicineDonationRepository;
import com.harera.hayat.donations.service.BaseService;
import com.harera.hayat.donations.service.DonationNotificationsService;
import com.harera.hayat.donations.service.ai.PredictionService;
import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.model.medicine.Medicine;
import com.harera.hayat.framework.model.medicine.MedicineUnit;
import com.harera.hayat.framework.repository.city.CityRepository;
import com.harera.hayat.framework.repository.repository.MedicineRepository;
import com.harera.hayat.framework.repository.repository.MedicineUnitRepository;
import com.harera.hayat.framework.service.file.CloudFileService;
import jakarta.ws.rs.BadRequestException;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

import static com.harera.hayat.framework.util.FileUtils.convertMultiPartToFile;

@Log4j2
@Service
public class MedicineDonationService extends BaseService {

    private final MedicineDonationValidation donationValidation;
    private final CityRepository cityRepository;
    private final MedicineUnitRepository medicineUnitRepository;
    private final ModelMapper modelMapper;
    private final MedicineDonationRepository medicineDonationRepository;
    private final MedicineRepository medicineRepository;
    private final CloudFileService cloudFileService;
    private final DonationNotificationsService donationNotificationsService;
    private final PredictionService predictionService;

    @Autowired
    public MedicineDonationService(MedicineDonationValidation donationValidation,
                    CityRepository cityRepository,
                    MedicineUnitRepository medicineUnitRepository,
                    ModelMapper modelMapper,
                    MedicineDonationRepository medicineDonationRepository,
                    MedicineRepository medicineRepository,
                    CloudFileService cloudFileService,
                    DonationNotificationsService donationNotificationsService,
                    PredictionService predictionService) {
        this.donationValidation = donationValidation;
        this.cityRepository = cityRepository;
        this.medicineUnitRepository = medicineUnitRepository;
        this.modelMapper = modelMapper;
        this.medicineDonationRepository = medicineDonationRepository;
        this.medicineRepository = medicineRepository;
        this.cloudFileService = cloudFileService;
        this.donationNotificationsService = donationNotificationsService;
        this.predictionService = predictionService;
    }

    public MedicineDonationResponse create(
                    MedicineDonationRequest medicineDonationRequest,
                    String authorization) {
        donationValidation.validateCreate(medicineDonationRequest);

        MedicineDonation medicineDonation =
                        modelMapper.map(medicineDonationRequest, MedicineDonation.class);
        medicineDonation.setStatus(DonationStatus.PENDING);
        medicineDonation.setCategory(DonationCategory.MEDICINE);
        medicineDonation.setCity(getCity(medicineDonationRequest.getCityId()));
        medicineDonation.setDonationDate(OffsetDateTime.now());
        medicineDonation.setUser(getUser(authorization));
        medicineDonation.setMedicineUnit(
                        getUnit(medicineDonationRequest.getMedicineUnitId()));
        medicineDonation.setMedicine(
                        getMedicine(medicineDonationRequest.getMedicineId()));

        medicineDonation.setDonationDate(OffsetDateTime.now());
        medicineDonation.setDonationExpirationDate(OffsetDateTime.now().plusDays(45));

        medicineDonationRepository.save(medicineDonation);

        donationNotificationsService.notifyProcessingDonation(medicineDonation);
        reviewDonation(medicineDonation);

        return modelMapper.map(medicineDonation, MedicineDonationResponse.class);
    }

    private void reviewDonation(MedicineDonation medicineDonation) {
        Prediction prediction = predictionService.predict(medicineDonation.getTitle()
                        + " " + medicineDonation.getDescription());
        if (Objects.equals(prediction.getLabel(), "MEDICINE")
                        && prediction.getCertainty() > 0.5) {
            medicineDonation.setStatus(DonationStatus.ACTIVE);
            donationNotificationsService.notifyDonationAccepted(medicineDonation);
        } else {
            donationNotificationsService.notifyDonationRejected(medicineDonation);
            medicineDonation.setStatus(DonationStatus.REJECTED);
        }
        medicineDonationRepository.save(medicineDonation);
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

    public List<MedicineDonationResponse> list(int page) {
        page = Math.max(page, 1) - 1;
        return medicineDonationRepository.findAll(Pageable.ofSize(16).withPage(page))
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
        medicineDonation.setStatus(DonationStatus.PENDING);
        medicineDonation.setCity(getCity(request.getCityId()));
        medicineDonation.setMedicineUnit(getUnit(request.getMedicineUnitId()));
        medicineDonation.setMedicine(getMedicine(request.getMedicineId()));

        // TODO: 28/02/23 get user from token
        // TODO: 28/02/23 send request to ai model

        medicineDonationRepository.save(medicineDonation);
        return modelMapper.map(medicineDonation, MedicineDonationResponse.class);

    }

    public List<MedicineDonationResponse> search(String query, int page) {
        page = Integer.max(page, 1) - 1;
        return medicineDonationRepository
                        .search(query, Pageable.ofSize(16).withPage(page)).stream()
                        .map(medicineDonation -> modelMapper.map(medicineDonation,
                                        MedicineDonationResponse.class))
                        .toList();
    }

    public MedicineDonationResponse updateImage(Long id, MultipartFile file) {
        MedicineDonation medicineDonation = medicineDonationRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                        MedicineDonation.class, id));

        String imageUrl = cloudFileService.uploadFile(convertMultiPartToFile(file));
        if (medicineDonation.getImageUrl() == null) {
            medicineDonation.setImageUrl(imageUrl);
        } else {
            cloudFileService.deleteFile(medicineDonation.getImageUrl());
            medicineDonation.setImageUrl(imageUrl);
        }

        medicineDonationRepository.save(medicineDonation);
        return modelMapper.map(medicineDonation, MedicineDonationResponse.class);
    }

    public void upvote(Long id, String authorization) {
        MedicineDonation medicineDonation = medicineDonationRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                        MedicineDonation.class, id));
        medicineDonation.upvote(getUser(authorization));
        medicineDonationRepository.save(medicineDonation);
    }

    public void downvote(Long id, String authorization) {
        MedicineDonation medicineDonation = medicineDonationRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                        MedicineDonation.class, id));
        medicineDonation.downvote(getUser(authorization));
        medicineDonationRepository.save(medicineDonation);
    }

    public void receive(Long id) {
        MedicineDonation medicineDonation = medicineDonationRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                        MedicineDonation.class, id));
        if (medicineDonation.getStatus() != DonationStatus.ACTIVE) {
            throw new BadRequestException("Donation is not active");
        }
        medicineDonation.setStatus(DonationStatus.DONE);
        medicineDonation.getUser()
                        .setReputation(medicineDonation.getUser().getReputation() + 50);
        medicineDonationRepository.save(medicineDonation);
    }
}
