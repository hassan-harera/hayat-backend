package com.harera.hayat.donations.service.clothing;

import com.harera.hayat.donations.model.CommunicationMethod;
import com.harera.hayat.donations.model.clothing.ClothingDonation;
import com.harera.hayat.donations.model.clothing.ClothingDonationRequest;
import com.harera.hayat.donations.model.clothing.ClothingDonationUpdateRequest;
import com.harera.hayat.donations.repository.clothing.ClothingDonationRepository;
import com.harera.hayat.donations.service.DonationValidation;
import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.exception.FieldFormatException;
import com.harera.hayat.framework.exception.MandatoryFieldException;
import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.repository.city.CityRepository;
import com.harera.hayat.framework.util.ErrorCode;
import com.harera.hayat.framework.util.FieldFormat;
import org.springframework.stereotype.Service;

import static com.harera.hayat.donations.util.CommunicationRegexUtils.isValidTelegramLink;
import static com.harera.hayat.donations.util.CommunicationRegexUtils.isValidWhatsappLink;
import static com.harera.hayat.framework.util.ErrorCode.NOT_FOUND_CITY;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class ClothingDonationValidation {

    private final DonationValidation donationValidation;
    private final ClothingDonationRepository clothingDonationRepository;
    private final CityRepository cityRepository;

    public ClothingDonationValidation(DonationValidation donationValidation,
                    ClothingDonationRepository clothingDonationRepository,
                    CityRepository cityRepository) {
        this.donationValidation = donationValidation;
        this.clothingDonationRepository = clothingDonationRepository;
        this.cityRepository = cityRepository;
    }

    public void validateCreate(ClothingDonationRequest clothingDonationRequest) {
        validateMandatory(clothingDonationRequest);
        validateFormat(clothingDonationRequest);
        validateExisting(clothingDonationRequest);
    }

    public void validateUpdate(Long id, ClothingDonationUpdateRequest request) {
        validateMandatory(request);
        validateFormat(request);
        validateExisting(request);
        validateUpdateExisting(id);
    }

    private void validateExisting(ClothingDonationRequest clothingDonationRequest) {
        if (!cityRepository.existsById(clothingDonationRequest.getCityId()))
            throw new EntityNotFoundException(City.class,
                            clothingDonationRequest.getCityId(), NOT_FOUND_CITY);
    }

    private void validateFormat(ClothingDonationRequest clothingDonationRequest) {
        if (clothingDonationRequest.getTitle().length() < 4
                        || clothingDonationRequest.getTitle().length() > 100) {
            throw new FieldFormatException(ErrorCode.FORMAT_DONATION_TITLE, "title",
                            FieldFormat.TITLE_PATTERN);
        }

        if (clothingDonationRequest.getTelegramLink() != null && !isValidTelegramLink(
                        clothingDonationRequest.getTelegramLink())) {
            throw new FieldFormatException("", "telegram_link",
                            clothingDonationRequest.getTelegramLink());
        }

        if (clothingDonationRequest.getWhatsappLink() != null && !isValidWhatsappLink(
                        clothingDonationRequest.getWhatsappLink())) {
            throw new FieldFormatException("", "whatsapp_link",
                            clothingDonationRequest.getWhatsappLink());
        }

        if (clothingDonationRequest.getQuantity() < 0
                        || clothingDonationRequest.getQuantity() > 10000) {
            throw new FieldFormatException(ErrorCode.FORMAT_CLOTHING_DONATION_QUANTITY,
                            "quantity", clothingDonationRequest.getQuantity().toString());
        }
    }

    private void validateMandatory(ClothingDonationRequest clothingDonationRequest) {
        if (isBlank(clothingDonationRequest.getTitle())) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_DONATION_TITLE,
                            "title");
        }

        if (clothingDonationRequest.getCommunicationMethod() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_DONATION_COMMUNICATION_METHOD,
                            "communication_method");
        }

        if (clothingDonationRequest.getCityId() == null) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_DONATION_CITY_ID,
                            "city_id");
        }

        if (clothingDonationRequest.getCommunicationMethod() == CommunicationMethod.CHAT
                        && isBlank(clothingDonationRequest.getTelegramLink())
                        && isBlank(clothingDonationRequest.getWhatsappLink())) {
            throw new MandatoryFieldException(null, "telegram_link or whatsapp_link");
        }

        if (clothingDonationRequest.getQuantity() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_CLOTHING_DONATION_QUANTITY, "quantity");
        }

        if (clothingDonationRequest.getClothingConditionId() == null) {
            throw new MandatoryFieldException("clothing_condition");
        }

        if (clothingDonationRequest.getClothingSeasonId() == null) {
            throw new MandatoryFieldException("clothing_season");
        }

        if (clothingDonationRequest.getClothingTypeId() == null) {
            throw new MandatoryFieldException("clothing_type");
        }

        if (clothingDonationRequest.getClothingCategoryId() == null) {
            throw new MandatoryFieldException("clothing_category");
        }
    }

    private void validateUpdateExisting(Long id) throws EntityNotFoundException {
        if (!clothingDonationRepository.existsById(id)) {
            throw new EntityNotFoundException(ClothingDonation.class, id,
                            ErrorCode.NOT_FOUND_CLOTHING_DONATION);
        }
    }
}
