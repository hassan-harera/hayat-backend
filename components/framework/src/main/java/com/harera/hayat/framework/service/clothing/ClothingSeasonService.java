package com.harera.hayat.framework.service.clothing;

import com.harera.hayat.framework.model.clothing.ClothingSeason;
import com.harera.hayat.framework.repository.clothing.ClothingSeasonRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClothingSeasonService {

    private final ClothingSeasonRepository clothingSeasonRepository;

    public ClothingSeasonService(ClothingSeasonRepository clothingSeasonRepository) {
        this.clothingSeasonRepository = clothingSeasonRepository;
    }

    @Cacheable(value = "clothing_seasons")
    public List<ClothingSeason> list() {
        return clothingSeasonRepository.findAll();
    }

    public ClothingSeason get(Long clothingSeasonId) {
        return clothingSeasonRepository.findById(clothingSeasonId).orElseThrow(
                        () -> new RuntimeException("Clothing season not found"));
    }
}
