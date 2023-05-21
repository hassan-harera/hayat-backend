package com.harera.hayat.needs.service.medicine;

import com.harera.hayat.framework.exception.DocumentNotFoundException;
import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.exception.FieldLimitException;
import com.harera.hayat.framework.exception.MandatoryFieldException;
import com.harera.hayat.framework.model.medicine.Medicine;
import com.harera.hayat.framework.model.medicine.MedicineUnit;
import com.harera.hayat.framework.repository.city.CityRepository;
import com.harera.hayat.framework.repository.repository.MedicineRepository;
import com.harera.hayat.framework.repository.repository.MedicineUnitRepository;
import com.harera.hayat.needs.model.medicine.MedicineNeed;
import com.harera.hayat.needs.model.medicine.MedicineNeedDto;
import com.harera.hayat.needs.model.medicine.MedicineNeedRequest;
import com.harera.hayat.needs.model.medicine.MedicineNeedUpdateRequest;
import com.harera.hayat.needs.repository.medicine.MedicineNeedRepository;
import com.harera.hayat.needs.service.NeedValidation;
import com.harera.hayat.needs.util.ErrorCode;
import org.springframework.stereotype.Service;

import static com.harera.hayat.framework.util.ErrorCode.NOT_FOUND_MEDICINE;
import static com.harera.hayat.framework.util.ErrorCode.NOT_FOUND_MEDICINE_UNIT;
import static com.harera.hayat.needs.util.ErrorCode.MANDATORY_MEDICINE_NEED_QUANTITY;

@Service
public class MedicineNeedValidation {

    private final NeedValidation needValidation;
    private final MedicineNeedRepository medicineNeedRepository;
    private final CityRepository cityRepository;
    private final MedicineRepository medicineRepository;
    private final MedicineUnitRepository medicineUnitRepository;

    public MedicineNeedValidation(NeedValidation needValidation,
                    MedicineNeedRepository medicineNeedRepository,
                    CityRepository cityRepository, MedicineRepository medicineRepository,
                    MedicineUnitRepository medicineUnitRepository) {
        this.needValidation = needValidation;
        this.medicineNeedRepository = medicineNeedRepository;
        this.cityRepository = cityRepository;
        this.medicineRepository = medicineRepository;
        this.medicineUnitRepository = medicineUnitRepository;
    }

    public void validateCreate(MedicineNeedRequest medicineNeedRequest) {
        needValidation.validateCreate(medicineNeedRequest);
        validateCreateMandatory(medicineNeedRequest);
        validateCreateFormat(medicineNeedRequest);
        validateCreateExisting(medicineNeedRequest);
    }

    public void validateUpdate(String id,
                    MedicineNeedUpdateRequest medicineNeedUpdateRequest) {
        needValidation.validateUpdate(medicineNeedUpdateRequest);
        validateUpdateMandatory(medicineNeedUpdateRequest);
        validateUpdateFormat(medicineNeedUpdateRequest);
        validateUpdateExisting(id, medicineNeedUpdateRequest);
    }

    private void validateUpdateMandatory(MedicineNeedUpdateRequest medicineNeedRequest) {
        validateMandatoryQuantity(medicineNeedRequest.getQuantity());
        validateMandatoryMedicine(medicineNeedRequest.getMedicineId());
        validateMandatoryMedicineUnit(medicineNeedRequest.getMedicineUnitId());
    }

    private void validateUpdateExisting(String id,
                    MedicineNeedUpdateRequest medicineNeedRequest) {
        if (medicineNeedRepository.countById(id) == 0) {
            throw new DocumentNotFoundException(MedicineNeed.class, id);
        }

        validateMedicineExisted(medicineNeedRequest.getMedicineId());
        validateMedicineUnitExisted(medicineNeedRequest.getMedicineUnitId());
    }

    private void validateCreateExisting(MedicineNeedRequest medicineNeedRequest) {
        validateMedicineExisted(medicineNeedRequest.getMedicineId());
        validateMedicineUnitExisted(medicineNeedRequest.getMedicineUnitId());
    }

    private void validateMedicineUnitExisted(Long medicineNeedRequest) {
        if (!medicineUnitRepository.existsById(medicineNeedRequest)) {
            throw new EntityNotFoundException(MedicineUnit.class, medicineNeedRequest,
                            NOT_FOUND_MEDICINE_UNIT);
        }
    }

    private void validateMedicineExisted(Long medicineNeedRequest) {
        if (!medicineRepository.existsById(medicineNeedRequest)) {
            throw new EntityNotFoundException(Medicine.class, medicineNeedRequest,
                            NOT_FOUND_MEDICINE);
        }
    }

    private void validateCreateFormat(MedicineNeedDto medicineNeedRequest) {
        validateQuantityFormat(medicineNeedRequest.getQuantity());
    }

    private void validateUpdateFormat(MedicineNeedUpdateRequest medicineNeedRequest) {
        validateQuantityFormat(medicineNeedRequest.getQuantity());
    }

    private void validateQuantityFormat(Float quantity) {
        if (quantity < 1 || quantity > 100000) {
            throw new FieldLimitException(ErrorCode.FORMAT_MEDICINE_NEED_QUANTITY,
                            "quantity", quantity);
        }
    }

    private void validateCreateMandatory(MedicineNeedRequest medicineNeedRequest) {
        validateMandatoryQuantity(medicineNeedRequest.getQuantity());
        validateMandatoryMedicine(medicineNeedRequest.getMedicineId());
        validateMandatoryMedicineUnit(medicineNeedRequest.getMedicineUnitId());
    }

    private static void validateMandatoryMedicineUnit(Long medicineNeedRequest) {
        if (medicineNeedRequest == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_MEDICINE_NEED_MEDICINE_UNIT_ID,
                            "medicine_unit_id");
        }
    }

    private static void validateMandatoryMedicine(Long medicineId) {
        if (medicineId == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_MEDICINE_NEED_MEDICINE_ID, "medicine_id");
        }
    }

    private static void validateMandatoryQuantity(Float quantity) {
        if (quantity == null) {
            throw new MandatoryFieldException(MANDATORY_MEDICINE_NEED_QUANTITY,
                            "quantity");
        }
    }
}
