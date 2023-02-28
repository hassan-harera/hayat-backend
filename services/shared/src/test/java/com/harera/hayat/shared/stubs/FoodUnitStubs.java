package com.harera.hayat.shared.stubs;

import com.harera.hayat.framework.model.food.FoodUnit;
import com.harera.hayat.framework.repository.food.FoodUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodUnitStubs {

    @Autowired
    private FoodUnitRepository repository;

    public FoodUnit insert(String arabicName, String englishName) {
        FoodUnit unit = create(arabicName, englishName);
        repository.save(unit);
        return unit;
    }

    private FoodUnit create(String arabicName, String englishName) {
        FoodUnit unit = new FoodUnit();
        unit.setArabicName(arabicName);
        unit.setEnglishName(englishName);
        return unit;
    }
}
