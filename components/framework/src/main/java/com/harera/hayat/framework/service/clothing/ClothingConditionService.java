package com.harera.hayat.framework.service.clothing;

import com.harera.hayat.framework.model.clothing.ClothingCondition;
import com.harera.hayat.framework.repository.clothing.ClothingConditionRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClothingConditionService {

    private final ClothingConditionRepository clothingConditionRepository;

    public ClothingConditionService(
                    ClothingConditionRepository clothingConditionRepository) {
        this.clothingConditionRepository = clothingConditionRepository;
    }

    @Cacheable("clothing_conditions")
    public List<ClothingCondition> list() {
        return clothingConditionRepository.findAll();
    }

    public ClothingCondition get(Long clothingConditionId) {
        return clothingConditionRepository.findById(clothingConditionId).orElseThrow(
                        () -> new RuntimeException("Clothing condition not found"));
    }
}
