package com.harera.hayat.framework.service;

import com.harera.hayat.framework.model.ClothingSeason;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.harera.hayat.framework.model.ClothingSeason.Season.*;

@Service
public class ClothingSeasonService {

    private static final List<ClothingSeason> list =
                    List.of(new ClothingSeason("صيف", "Summer", SUMMER),
                                    new ClothingSeason("شتاء", "Winter", WINTER),
                                    new ClothingSeason("ربيع", "Spring", SPRING),
                                    new ClothingSeason("خريف", "Autumn", AUTUMN));

    public List<ClothingSeason> list() {
        return list;
    }
}
