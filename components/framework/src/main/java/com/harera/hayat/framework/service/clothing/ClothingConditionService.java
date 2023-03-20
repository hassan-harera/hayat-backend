package com.harera.hayat.framework.service.clothing;

import com.harera.hayat.framework.model.ClothingCondition;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.harera.hayat.framework.model.ClothingCondition.Condition.NEW;

@Service
public class ClothingConditionService {

    private static final List<ClothingCondition> list =
                    List.of(new ClothingCondition("جديد", "New", NEW),
                                    new ClothingCondition("مستخدم", "Used",
                                                    ClothingCondition.Condition.USED),
                                    new ClothingCondition("مزيج", "Mixed",
                                                    ClothingCondition.Condition.MIXED));

    public List<ClothingCondition> list() {
        return list;
    }
}
