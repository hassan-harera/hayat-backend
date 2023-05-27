package com.harera.hayat.donations.stubs.clothing;

import com.harera.hayat.donations.model.CommunicationMethod;
import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.DonationStatus;
import com.harera.hayat.donations.model.clothing.*;
import com.harera.hayat.donations.repository.clothing.ClothingDonationRepository;
import com.harera.hayat.framework.model.clothing.ClothingCondition;
import com.harera.hayat.framework.model.clothing.ClothingType;
import com.harera.hayat.framework.model.clothing.ClothingSeason;
import com.harera.hayat.framework.model.clothing.ClothingSize;
import com.harera.hayat.framework.model.city.City;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClothingDonationStubs {

    private final ClothingDonationRepository clothingDonationRepository;

    public ClothingDonation create(String title, String description,
                                   LocalDateTime donationDate, LocalDateTime donationExpirationDate,
                                   DonationCategory category, DonationStatus status,
                                   CommunicationMethod communicationMethod, int quantity,
                                   ClothingCondition condition, ClothingType gender,
                                   ClothingSize clothingSize, ClothingSeason clothingSeason, City city) {
        ClothingDonation clothingDonation = new ClothingDonation();
        clothingDonation.setId(0L);
        clothingDonation.setTitle(title);
        clothingDonation.setDescription(description);
        clothingDonation.setDonationDate(donationDate);
        clothingDonation.setDonationExpirationDate(donationExpirationDate);
        clothingDonation.setCategory(category);
        clothingDonation.setStatus(status);
        clothingDonation.setCommunicationMethod(communicationMethod);
        clothingDonation.setCity(city);
        clothingDonation.setQuantity(quantity);
        clothingDonation.setClothingCondition(condition);
        clothingDonation.setClothingType(gender);
        clothingDonation.setClothingSize(clothingSize);
        clothingDonation.setClothingSeason(clothingSeason);
        return clothingDonation;
    }

    public ClothingDonation insert(String title, String description,
                    LocalDateTime donationDate, LocalDateTime donationExpirationDate,
                    DonationCategory category, DonationStatus status,
                    CommunicationMethod communicationMethod, int quantity,
                    ClothingCondition condition, ClothingType gender,
                    ClothingSize clothingSize, ClothingSeason clothingSeason, City city) {
        ClothingDonation clothingDonation = create(title, description, donationDate,
                        donationExpirationDate, category, status, communicationMethod,
                        quantity, condition, gender, clothingSize, clothingSeason, city
        );
        return clothingDonationRepository.save(clothingDonation);
    }

    public ClothingDonation get(Long id) {
        return clothingDonationRepository.findById(id).orElse(null);
    }
}
