package com.harera.hayat.donations.service.clothing;

import com.harera.hayat.donations.model.clothing.ClothingDonation;
import com.harera.hayat.donations.model.clothing.ClothingDonationDto;
import com.harera.hayat.donations.model.clothing.ClothingDonationRequest;
import com.harera.hayat.donations.model.clothing.ClothingDonationUpdateRequest;
import com.harera.hayat.donations.repository.clothing.ClothingDonationRepository;
import com.harera.hayat.donations.service.DonationValidation;
import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.exception.FieldFormatException;
import com.harera.hayat.framework.exception.MandatoryFieldException;
import com.harera.hayat.framework.util.ErrorCode;
import org.springframework.stereotype.Service;

@Service
public class ClothingDonationValidation {

    private final DonationValidation donationValidation;
    private final ClothingDonationRepository clothingDonationRepository;

    public ClothingDonationValidation(DonationValidation donationValidation,
                    ClothingDonationRepository clothingDonationRepository) {
        this.donationValidation = donationValidation;
        this.clothingDonationRepository = clothingDonationRepository;
    }

    public void validateCreate(ClothingDonationRequest clothingDonationRequest) {
        donationValidation.validateCreate(clothingDonationRequest);
        validateMandatory(clothingDonationRequest);
        validateFormat(clothingDonationRequest);
    }

    public void validateUpdate(Long id, ClothingDonationUpdateRequest request) {
        donationValidation.validateUpdate(request);
        validateMandatory(request);
        validateFormat(request);
        validateUpdateExisting(id);
    }

    private void validateFormat(ClothingDonationDto clothingDonationRequest) {
        if (clothingDonationRequest.getQuantity() < 0
                        || clothingDonationRequest.getQuantity() > 10000) {
            throw new FieldFormatException(ErrorCode.FORMAT_CLOTHING_DONATION_QUANTITY,
                            "quantity", clothingDonationRequest.getQuantity().toString());
        }
    }

    private void validateMandatory(ClothingDonationDto clothingDonationRequest) {
        if (clothingDonationRequest.getQuantity() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_CLOTHING_DONATION_QUANTITY, "quantity");
        }

        if (clothingDonationRequest.getClothingConditionId() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_CLOTHING_DONATION_CONDITION,
                            "clothing_condition");
        }
    }

    private void validateUpdateExisting(Long id) throws EntityNotFoundException {
        if (!clothingDonationRepository.existsById(id)) {
            throw new EntityNotFoundException(ClothingDonation.class, id,
                            ErrorCode.NOT_FOUND_CLOTHING_DONATION);
        }
    }
}
