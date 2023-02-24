package com.harera.hayat.needs.stubs.possession;


import org.springframework.stereotype.Service;

import com.harera.hayat.needs.model.possession.PossessionCategory;
import com.harera.hayat.needs.repository.possession.PossessionCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PossessionCategoryStubs {

    private final PossessionCategoryRepository categoryRepository;

    public PossessionCategory insert(
            String arabicName,
            String englishName
    ) {
        PossessionCategory bookNeed = create(
                0L,
                arabicName,
                englishName
        );
        return categoryRepository.save(bookNeed);
    }

    public PossessionCategory create(
            Long id,
            String arabicName,
            String englishName
    ) {
        PossessionCategory possessionCategory = new PossessionCategory();
        possessionCategory.setId(id);
        possessionCategory.setArabicName(arabicName);
        possessionCategory.setEnglishName(englishName);
        return possessionCategory;
    }
}
