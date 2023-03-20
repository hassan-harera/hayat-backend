package com.harera.hayat.donations.service.medicine;

import com.harera.hayat.donations.model.medicine.MedicineDonation;
import com.harera.hayat.donations.model.medicine.MedicineDonationDto;
import com.harera.hayat.donations.model.medicine.MedicineDonationRequest;
import com.harera.hayat.donations.model.medicine.MedicineDonationUpdateRequest;
import com.harera.hayat.donations.repository.medicine.MedicineDonationRepository;
import com.harera.hayat.donations.service.DonationValidation;
import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.exception.FieldLimitException;
import com.harera.hayat.framework.exception.MandatoryFieldException;
import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.model.medicine.Medicine;
import com.harera.hayat.framework.model.medicine.MedicineUnit;
import com.harera.hayat.framework.repository.city.CityRepository;
import com.harera.hayat.framework.repository.repository.MedicineRepository;
import com.harera.hayat.framework.repository.repository.MedicineUnitRepository;
import com.harera.hayat.framework.util.ErrorCode;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class MedicineDonationValidation {

    private final DonationValidation donationValidation;
    private final MedicineDonationRepository medicineDonationRepository;
    private final CityRepository cityRepository;
    private final MedicineRepository medicineRepository;
    private final MedicineUnitRepository medicineUnitRepository;

    public MedicineDonationValidation(DonationValidation donationValidation,
                    MedicineDonationRepository medicineDonationRepository,
                    CityRepository cityRepository, MedicineRepository medicineRepository,
                    MedicineUnitRepository medicineUnitRepository) {
        this.donationValidation = donationValidation;
        this.medicineDonationRepository = medicineDonationRepository;
        this.cityRepository = cityRepository;
        this.medicineRepository = medicineRepository;
        this.medicineUnitRepository = medicineUnitRepository;
    }

    public void validateCreate(MedicineDonationRequest medicineDonationRequest) {
        donationValidation.validateCreate(medicineDonationRequest);
        validateMandatory(medicineDonationRequest);
        validateCreateFormat(medicineDonationRequest);
        validateCreateExisting(medicineDonationRequest);
    }

    public void validateUpdate(Long id,
                    MedicineDonationUpdateRequest medicineDonationRequest) {
        donationValidation.validateUpdate(medicineDonationRequest);
        validateMandatory(medicineDonationRequest);
        validateUpdateFormat(medicineDonationRequest);
        validateUpdateExisting(id, medicineDonationRequest);
    }

    private void validateUpdateExisting(Long id,
                    MedicineDonationUpdateRequest medicineDonationRequest) {
        if (!medicineDonationRepository.existsById(id)) {
            throw new EntityNotFoundException(MedicineDonation.class, id);
        }

        if (!cityRepository.existsById(medicineDonationRequest.getCityId())) {
            throw new EntityNotFoundException(City.class,
                            medicineDonationRequest.getCityId());
        }

        if (!medicineRepository.existsById(medicineDonationRequest.getMedicineId())) {
            throw new EntityNotFoundException(Medicine.class,
                            medicineDonationRequest.getMedicineId());
        }

        if (!medicineUnitRepository
                        .existsById(medicineDonationRequest.getMedicineUnitId())) {
            throw new EntityNotFoundException(MedicineUnit.class,
                            medicineDonationRequest.getMedicineUnitId());
        }
    }

    private void validateCreateExisting(MedicineDonationRequest medicineDonationRequest) {
        if (!cityRepository.existsById(medicineDonationRequest.getCityId())) {
            throw new EntityNotFoundException(City.class,
                            medicineDonationRequest.getCityId(),
                            ErrorCode.NOT_FOUND_CITY);
        }

        if (!medicineRepository.existsById(medicineDonationRequest.getMedicineId())) {
            throw new EntityNotFoundException(Medicine.class,
                            medicineDonationRequest.getMedicineId(),
                            ErrorCode.NOT_FOUND_MEDICINE);
        }

        if (!medicineUnitRepository
                        .existsById(medicineDonationRequest.getMedicineUnitId())) {
            throw new EntityNotFoundException(MedicineUnit.class,
                            medicineDonationRequest.getMedicineUnitId(),
                            ErrorCode.NOT_FOUND_MEDICINE_UNIT);
        }
    }

    private void validateCreateFormat(MedicineDonationDto medicineDonationRequest) {
        if (medicineDonationRequest.getMedicineExpirationDate()
                        .isBefore(OffsetDateTime.now())) {
            throw new FieldLimitException(
                            ErrorCode.FORMAT_MEDICINE_DONATION_EXPIRATION_DATE,
                            "medicine expiration date", medicineDonationRequest
                                            .getMedicineExpirationDate().toString());
        }

        if (medicineDonationRequest.getQuantity() < 0
                        || medicineDonationRequest.getQuantity() > 100000) {
            throw new FieldLimitException(ErrorCode.FORMAT_MEDICINE_DONATION_AMOUNT,
                            "quantity",
                            String.valueOf(medicineDonationRequest.getQuantity()));
        }

        if (medicineDonationRequest.getMedicineExpirationDate()
                        .isBefore(OffsetDateTime.now())) {
            throw new FieldLimitException(
                            ErrorCode.FORMAT_MEDICINE_DONATION_EXPIRATION_DATE,
                            "medicine expiration date", medicineDonationRequest
                                            .getMedicineExpirationDate().toString());
        }
    }

    private void validateUpdateFormat(
                    MedicineDonationUpdateRequest medicineDonationRequest) {
        if (medicineDonationRequest.getMedicineExpirationDate()
                        .isBefore(OffsetDateTime.now())) {
            throw new FieldLimitException(
                            ErrorCode.FORMAT_MEDICINE_DONATION_EXPIRATION_DATE,
                            "medicine expiration date", medicineDonationRequest
                                            .getMedicineExpirationDate().toString());
        }

        if (medicineDonationRequest.getQuantity() < 0
                        || medicineDonationRequest.getQuantity() > 100000) {
            throw new FieldLimitException(ErrorCode.FORMAT_MEDICINE_DONATION_AMOUNT,
                            "quantity",
                            String.valueOf(medicineDonationRequest.getQuantity()));
        }

        if (medicineDonationRequest.getMedicineExpirationDate()
                        .isBefore(OffsetDateTime.now())) {
            throw new FieldLimitException(
                            ErrorCode.FORMAT_MEDICINE_DONATION_EXPIRATION_DATE,
                            "medicine_expiration_date", medicineDonationRequest
                                            .getMedicineExpirationDate().toString());
        }
    }

    private void validateMandatory(MedicineDonationDto medicineDonationRequest) {
        if (medicineDonationRequest.getQuantity() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_MEDICINE_DONATION_AMOUNT, "quantity");
        }

        if (medicineDonationRequest.getMedicineUnitId() == null) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_MEDICINE_DONATION_UNIT,
                            "unit_id");
        }

        if (medicineDonationRequest.getMedicineId() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_MEDICINE_DONATION_MEDICINE,
                            "medicine_id");
        }
        if (medicineDonationRequest.getMedicineExpirationDate() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_MEDICINE_DONATION_MEDICINE_EXPIRATION_DATE,
                            "medicine_expiration_date");
        }
    }
}
