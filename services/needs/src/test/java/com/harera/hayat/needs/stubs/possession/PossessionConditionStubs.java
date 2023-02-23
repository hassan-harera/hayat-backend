package com.harera.hayat.needs.stubs.possession;


import com.harera.hayat.needs.model.possession.PossessionCondition;
import com.harera.hayat.needs.repository.possession.PossessionConditionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PossessionConditionStubs {

    private final PossessionConditionRepository conditionRepository;

    public PossessionCondition insert(
            String arabicName,
            String englishName
    ) {
        PossessionCondition bookNeed = create(
                0L,
                arabicName,
                englishName
        );
        return conditionRepository.save(bookNeed);
    }

    public PossessionCondition create(
            Long id,
            String arabicName,
            String englishName
    ) {
        PossessionCondition possessionCondition = new PossessionCondition();
        possessionCondition.setId(id);
        possessionCondition.setArabicName(arabicName);
        possessionCondition.setEnglishName(englishName);
        return possessionCondition;
    }
}
