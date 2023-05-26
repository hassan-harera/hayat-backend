package com.harera.hayat.framework.service.clothing;

import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.model.clothing.ClothingSize;
import com.harera.hayat.framework.repository.clothing.ClothingSizeRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClothingSizeService {

    private final ClothingSizeRepository clothingSizeRepository;

    public ClothingSizeService(ClothingSizeRepository clothingSizeRepository) {
        this.clothingSizeRepository = clothingSizeRepository;
    }

    @Cacheable("clothing_sizes")
    public List<ClothingSize> list() {
        return clothingSizeRepository.findAll();
    }

    public ClothingSize get(Long clothingSizeId) {
        return clothingSizeRepository.findById(clothingSizeId).orElseThrow(
                        () -> new EntityNotFoundException("Clothing size not found"));
    }
}
