package com.harera.hayat.needs.service;

import static org.apache.commons.lang3.StringUtils.isBlank;

import com.harera.hayat.needs.model.medicine.MedicineNeedRequest;
import com.harera.hayat.needs.model.medicine.MedicineNeedUpdateRequest;
import org.springframework.stereotype.Service;

import com.harera.hayat.framework.exception.FieldFormatException;
import com.harera.hayat.framework.exception.MandatoryFieldException;
import com.harera.hayat.framework.repository.city.CityRepository;
import com.harera.hayat.framework.util.FieldFormat;
import com.harera.hayat.needs.model.NeedDto;
import com.harera.hayat.needs.util.ErrorCode;

@Service
public class NeedValidation {

    private final CityRepository cityRepository;

    public NeedValidation(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public void validate(NeedDto needDto) {
        validateMandatory(needDto);
        validateFormat(needDto);
    }

    private void validateFormat(NeedDto needDto) {
        if (needDto.getTitle().length() < 4 || needDto.getTitle().length() > 100) {
            throw new FieldFormatException(ErrorCode.FORMAT_NEED_TITLE, "title",
                            FieldFormat.TITLE_PATTERN);
        }
    }

    private void validateMandatory(NeedDto needDto) {
        if (isBlank(needDto.getTitle())) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_NEED_TITLE, "title");
        }

        if (needDto.getCommunicationMethod() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_NEED_COMMUNICATION_METHOD,
                            "communication_method");
        }

        if (needDto.getCityId() == null) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_NEED_CITY_ID,
                            "city_id");
        }
    }

    public void validateCreate(MedicineNeedRequest medicineNeedRequest) {
        validateMandatory(medicineNeedRequest);
        validateFormat(medicineNeedRequest);
    }

    public void validateUpdate(MedicineNeedUpdateRequest medicineNeedRequest) {
        validateMandatory(medicineNeedRequest);
        validateFormat(medicineNeedRequest);
    }
}
