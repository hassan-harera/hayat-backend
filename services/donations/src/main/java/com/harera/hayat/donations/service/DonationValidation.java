package com.harera.hayat.donations.service;

import com.harera.hayat.donations.model.DonationDto;
import com.harera.hayat.framework.exception.FieldFormatException;
import com.harera.hayat.framework.exception.MandatoryFieldException;
import com.harera.hayat.framework.util.ErrorCode;
import com.harera.hayat.framework.util.FieldFormat;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class DonationValidation {

    public void validateCreate(DonationDto donationDto) {
        validateMandatory(donationDto);
        validateFormat(donationDto);
    }

    public void validateUpdate(DonationDto donationDto) {
        validateMandatory(donationDto);
        validateFormat(donationDto);
    }

    private void validateFormat(DonationDto donationDto) {
        if (donationDto.getTitle().length() < 4
                        || donationDto.getTitle().length() > 100) {
            throw new FieldFormatException(ErrorCode.FORMAT_DONATION_TITLE, "title",
                            FieldFormat.TITLE_PATTERN);
        }
    }

    private void validateMandatory(DonationDto donationDto) {
        if (isBlank(donationDto.getTitle())) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_DONATION_TITLE,
                            "title");
        }
        if (donationDto.getCommunicationMethod() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_DONATION_COMMUNICATION_METHOD,
                            "communication_method");
        }
        if (donationDto.getCityId() == null) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_DONATION_CITY_ID,
                            "city_id");
        }
    }
}
