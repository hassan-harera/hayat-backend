package com.harera.hayat.framework.service.clothing;

import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.model.clothing.ClothingCategory;
import com.harera.hayat.framework.repository.clothing.ClothingCategoryRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClothingCategoryService {

    private final ClothingCategoryRepository clothingConditionRepository;

    public ClothingCategoryService(
                    ClothingCategoryRepository clothingConditionRepository) {
        this.clothingConditionRepository = clothingConditionRepository;
    }

    @Cacheable("clothing_categories")
    public List<ClothingCategory> list() {
        return clothingConditionRepository.findAll();
    }

    public ClothingCategory get(Long clothingCategoryId) {
        return clothingConditionRepository.findById(clothingCategoryId).orElseThrow(
                        () -> new EntityNotFoundException("Clothing category not found"));
    }
}
