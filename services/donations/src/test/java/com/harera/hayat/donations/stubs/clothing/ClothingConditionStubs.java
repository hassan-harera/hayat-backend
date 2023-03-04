package com.harera.hayat.donations.stubs.clothing;

import com.harera.hayat.donations.model.clothing.*;
import com.harera.hayat.donations.repository.clothing.ClothingConditionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClothingConditionStubs {

    private final ClothingConditionRepository clothingConditionRepository;

    public ClothingCondition create(String arabicName, String englishName) {
        ClothingCondition clothingDonation = new ClothingCondition();
        clothingDonation.setId(0L);
        clothingDonation.setArabicName(arabicName);
        clothingDonation.setEnglishName(englishName);
        return clothingDonation;
    }

    public ClothingCondition insert(String arabicName, String englishName) {
        ClothingCondition clothingCondition = create(arabicName, englishName);
        return clothingConditionRepository.save(clothingCondition);
    }

    public ClothingCondition get(Long id) {
        return clothingConditionRepository.findById(id).orElse(null);
    }
}
