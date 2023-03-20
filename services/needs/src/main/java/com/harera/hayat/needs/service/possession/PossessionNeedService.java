package com.harera.hayat.needs.service.possession;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.harera.hayat.framework.service.city.CityService;
import com.harera.hayat.needs.model.NeedCategory;
import com.harera.hayat.needs.model.NeedState;
import com.harera.hayat.needs.model.possession.PossessionNeed;
import com.harera.hayat.needs.model.possession.PossessionNeedRequest;
import com.harera.hayat.needs.model.possession.PossessionNeedResponse;
import com.harera.hayat.needs.repository.possession.PossessionCategoryRepository;
import com.harera.hayat.needs.repository.possession.PossessionConditionRepository;
import com.harera.hayat.needs.repository.possession.PossessionNeedRepository;
import com.harera.hayat.needs.service.BaseService;

@Service
public class PossessionNeedService implements BaseService {

    private final PossessionValidation possessionValidation;
    private final ModelMapper modelMapper;
    private final CityService cityService;
    private final int possessionNeedExpirationDays;
    private final PossessionNeedRepository possessionNeedRepository;
    private final PossessionCategoryRepository possessionCategoryRepository;
    private final PossessionConditionRepository possessionConditionRepository;

    public PossessionNeedService(PossessionValidation possessionValidation, ModelMapper modelMapper,
                                 CityService cityService, @Value("${needs.possession.expiration_days}") String possessionNeedExpirationDays, PossessionNeedRepository possessionNeedRepository, PossessionCategoryRepository possessionCategoryRepository, PossessionConditionRepository possessionConditionRepository) {
        this.possessionValidation = possessionValidation;
        this.modelMapper = modelMapper;
        this.cityService = cityService;
        this.possessionNeedExpirationDays = Integer.parseInt(possessionNeedExpirationDays);
        this.possessionNeedRepository = possessionNeedRepository;
        this.possessionCategoryRepository = possessionCategoryRepository;
        this.possessionConditionRepository = possessionConditionRepository;
    }

    public PossessionNeedResponse create(PossessionNeedRequest possessionNeedRequest) {
        possessionValidation.validateCreate(possessionNeedRequest);

        PossessionNeed possessionNeed = modelMapper.map(possessionNeedRequest, PossessionNeed.class);
        possessionNeed.setNeedDate(LocalDateTime.now());
        possessionNeed.setNeedExpirationDate(LocalDateTime.now().plusDays(possessionNeedExpirationDays));
        possessionNeed.setCategory(NeedCategory.POSSESSION);
        // TODO: set user from request authorization header
        possessionNeed.setCity(cityService.getCity(possessionNeedRequest.getCityId()));
        possessionNeed.setPossessionCategory(possessionCategoryRepository.findById(
                possessionNeedRequest.getPossessionCategoryId()).get());
        possessionNeed.setPossessionCondition(possessionConditionRepository.findById(
                possessionNeedRequest.getPossessionConditionId()).get());
        possessionNeed.setStatus(NeedState.PENDING);

        possessionNeed = possessionNeedRepository.save(possessionNeed);
        // TODO: send request to ml service
        return modelMapper.map(possessionNeedRepository.save(possessionNeed), PossessionNeedResponse.class);
    }
}
