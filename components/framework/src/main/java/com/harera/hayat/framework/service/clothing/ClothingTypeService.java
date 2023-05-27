package com.harera.hayat.framework.service.clothing;

import com.harera.hayat.framework.model.clothing.ClothingType;
import com.harera.hayat.framework.repository.clothing.ClothingTypeRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClothingTypeService {

    private final ClothingTypeRepository clothingTypeRepository;

    public ClothingTypeService(ClothingTypeRepository clothingTypeRepository) {
        this.clothingTypeRepository = clothingTypeRepository;
    }

    @Cacheable("clothing_types")
    public List<ClothingType> list() {
        return clothingTypeRepository.findAll();
    }

    public ClothingType get(Long clothingTypeId) {
        return clothingTypeRepository.findById(clothingTypeId).orElseThrow(
                        () -> new RuntimeException("Clothing type not found"));
    }
}
