package com.harera.hayat.framework.service.clothing;

import com.harera.hayat.framework.model.ClothingType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClothingTypeService {

    private static final List<ClothingType> list = List.of(
                    new ClothingType("رجالي", "Men", ClothingType.Type.MEN),
                    new ClothingType("حريمي", "Women", ClothingType.Type.WOMEN),
                    new ClothingType("أطفال", "Kids", ClothingType.Type.KIDS),
                    new ClothingType("اولادي", "Boys", ClothingType.Type.BOYS),
                    new ClothingType("بناتي", "Girls", ClothingType.Type.GIRLS),
                    new ClothingType("مزيج", "Mixed", ClothingType.Type.MIXED));

    public List<ClothingType> list() {
        return list;
    }
}
