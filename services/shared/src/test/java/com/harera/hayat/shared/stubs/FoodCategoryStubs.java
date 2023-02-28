package com.harera.hayat.shared.stubs;

import com.harera.hayat.framework.model.food.FoodCategory;
import com.harera.hayat.framework.repository.food.FoodCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FoodCategoryStubs {

    @Autowired
    private FoodCategoryRepository repository;

    public FoodCategory insert(String arabicName, String englishName) {
        FoodCategory category = create(arabicName, englishName);
        repository.save(category);
        return category;
    }

    private FoodCategory create(String arabicName, String englishName) {
        FoodCategory category = new FoodCategory();
        category.setArabicName(arabicName);
        category.setEnglishName(englishName);
        return category;
    }
}
