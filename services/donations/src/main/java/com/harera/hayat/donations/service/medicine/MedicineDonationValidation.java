package com.harera.hayat.donations.service.medicine;

import com.harera.hayat.donations.model.medicine.MedicineDonationRequest;
import com.harera.hayat.donations.service.DonationValidation;
import com.harera.hayat.framework.exception.FieldLimitException;
import com.harera.hayat.framework.exception.MandatoryFieldException;
import com.harera.hayat.framework.util.ErrorCode;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class MedicineDonationValidation extends DonationValidation {

    public void validateCreate(MedicineDonationRequest medicineDonationRequest) {
        validateDonation(medicineDonationRequest);
        validateMandatoryCreate(medicineDonationRequest);
        validateFormatCreate(medicineDonationRequest);
    }

    private void validateFormatCreate(MedicineDonationRequest medicineDonationRequest) {
        if (medicineDonationRequest.getMedicineExpirationDate()
                        .isBefore(OffsetDateTime.now())) {
            throw new FieldLimitException(ErrorCode.FORMAT_DONATION_EXPIRATION_DATE,
                            "medicine expiration date", medicineDonationRequest
                                            .getMedicineExpirationDate().toString());
        }

        if (medicineDonationRequest.getAmount() < 0
                        || medicineDonationRequest.getAmount() > 100000) {
            throw new FieldLimitException(ErrorCode.FORMAT_DONATION_AMOUNT, "amount",
                            String.valueOf(medicineDonationRequest.getAmount()));
        }
    }

    private void validateMandatoryCreate(
                    MedicineDonationRequest medicineDonationRequest) {
        if (medicineDonationRequest.getAmount() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_MEDICINE_DONATION_AMOUNT, "amount");
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
