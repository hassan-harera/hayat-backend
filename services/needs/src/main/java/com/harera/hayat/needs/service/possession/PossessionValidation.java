package com.harera.hayat.needs.service.possession;


import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.exception.MandatoryFieldException;
import com.harera.hayat.framework.repository.city.CityRepository;
import com.harera.hayat.needs.model.possession.PossessionCategory;
import com.harera.hayat.needs.model.possession.PossessionCondition;
import com.harera.hayat.needs.model.possession.PossessionNeedRequest;
import com.harera.hayat.needs.repository.possession.PossessionCategoryRepository;
import com.harera.hayat.needs.repository.possession.PossessionConditionRepository;
import com.harera.hayat.needs.service.NeedValidation;
import com.harera.hayat.needs.util.ErrorCode;
import org.springframework.stereotype.Service;

@Service
public class PossessionValidation extends NeedValidation {

    private final PossessionCategoryRepository possessionCategoryRepository;
    private final PossessionConditionRepository possessionConditionRepository;

    public PossessionValidation(CityRepository cityRepository, PossessionCategoryRepository possessionCategoryRepository, PossessionConditionRepository possessionConditionRepository) {
        super(cityRepository);
        this.possessionCategoryRepository = possessionCategoryRepository;
        this.possessionConditionRepository = possessionConditionRepository;
    }

    public void validateCreate(PossessionNeedRequest possessionNeedRequest) {
        super.validate(possessionNeedRequest);
        validateMandatoryCreate(possessionNeedRequest);
        validateExistingCreate(possessionNeedRequest);
    }

    private void validateExistingCreate(PossessionNeedRequest possessionNeedRequest) {
        if (!possessionCategoryRepository.existsById(possessionNeedRequest.getPossessionCategoryId()))
            throw new EntityNotFoundException(PossessionCategory.class, possessionNeedRequest.getPossessionCategoryId());
        if (!possessionConditionRepository.existsById(possessionNeedRequest.getPossessionConditionId()))
            throw new EntityNotFoundException(PossessionCondition.class, possessionNeedRequest.getPossessionCategoryId());
    }

    private void validateMandatoryCreate(PossessionNeedRequest possessionNeedRequest) {
        if (possessionNeedRequest.getPossessionCategoryId() == null)
            throw new MandatoryFieldException(ErrorCode.MANDATORY_POSSESSION_NEED_CATEGORY_ID, "category_id");
        if (possessionNeedRequest.getPossessionConditionId() == null)
            throw new MandatoryFieldException(ErrorCode.MANDATORY_POSSESSION_NEED_CONDITION_ID, "condition_id");
    }
}
