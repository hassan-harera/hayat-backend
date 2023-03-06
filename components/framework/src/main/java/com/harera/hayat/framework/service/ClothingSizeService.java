package com.harera.hayat.framework.service;

import com.harera.hayat.framework.model.ClothingSize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClothingSizeService {

    private static final List<ClothingSize> list = List.of(
                    new ClothingSize("صغير", "Small", ClothingSize.Size.S),
                    new ClothingSize("متوسط", "Medium", ClothingSize.Size.M),
                    new ClothingSize("كبير", "Large", ClothingSize.Size.L),
                    new ClothingSize("كبير جدا", "Extra Large", ClothingSize.Size.XL));

    public List<ClothingSize> list() {
        return list;
    }
}
