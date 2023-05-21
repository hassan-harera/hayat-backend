package com.harera.hayat.needs.service.possession;

import com.harera.hayat.framework.exception.DocumentNotFoundException;
import com.harera.hayat.framework.model.user.BaseUserDto;
import com.harera.hayat.framework.service.city.CityService;
import com.harera.hayat.needs.model.NeedCategory;
import com.harera.hayat.needs.model.NeedStatus;
import com.harera.hayat.needs.model.possession.PossessionNeed;
import com.harera.hayat.needs.model.possession.PossessionNeedRequest;
import com.harera.hayat.needs.model.possession.PossessionNeedResponse;
import com.harera.hayat.needs.repository.possession.PossessionCategoryRepository;
import com.harera.hayat.needs.repository.possession.PossessionConditionRepository;
import com.harera.hayat.needs.repository.possession.PossessionNeedRepository;
import com.harera.hayat.needs.service.BaseService;
import com.harera.hayat.needs.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PossessionNeedService implements BaseService {

    private final PossessionValidation possessionValidation;
    private final ModelMapper modelMapper;
    private final CityService cityService;
    private final int possessionNeedExpirationDays;
    private final PossessionNeedRepository possessionNeedRepository;
    private final PossessionCategoryRepository possessionCategoryRepository;
    private final PossessionConditionRepository possessionConditionRepository;
    private final UserService userService;

    public PossessionNeedService(PossessionValidation possessionValidation,
                    ModelMapper modelMapper, CityService cityService,
                    @Value("${needs.possession.expiration_days}") String possessionNeedExpirationDays,
                    PossessionNeedRepository possessionNeedRepository,
                    PossessionCategoryRepository possessionCategoryRepository,
                    PossessionConditionRepository possessionConditionRepository,
                    UserService userService) {
        this.possessionValidation = possessionValidation;
        this.modelMapper = modelMapper;
        this.cityService = cityService;
        this.possessionNeedExpirationDays =
                        Integer.parseInt(possessionNeedExpirationDays);
        this.possessionNeedRepository = possessionNeedRepository;
        this.possessionCategoryRepository = possessionCategoryRepository;
        this.possessionConditionRepository = possessionConditionRepository;
        this.userService = userService;
    }

    public PossessionNeedResponse create(PossessionNeedRequest possessionNeedRequest,
                    String authorization) {
        possessionValidation.validateCreate(possessionNeedRequest);

        PossessionNeed possessionNeed =
                        modelMapper.map(possessionNeedRequest, PossessionNeed.class);
        possessionNeed.setNeedDate(LocalDateTime.now());
        possessionNeed.setNeedExpirationDate(
                        LocalDateTime.now().plusDays(possessionNeedExpirationDays));
        possessionNeed.setCategory(NeedCategory.POSSESSION);
        possessionNeed.setUser(modelMapper.map(userService.getUser(authorization),
                        BaseUserDto.class));
        possessionNeed.setCity(cityService.get(possessionNeedRequest.getCityId()));
        possessionNeed.setPossessionCategory(possessionCategoryRepository
                        .findById(possessionNeedRequest.getPossessionCategoryId()).get());
        possessionNeed.setPossessionCondition(possessionConditionRepository
                        .findById(possessionNeedRequest.getPossessionConditionId())
                        .get());
        possessionNeed.setStatus(NeedStatus.PENDING);

        possessionNeed = possessionNeedRepository.save(possessionNeed);
        // TODO: send request to ml service
        return modelMapper.map(possessionNeedRepository.save(possessionNeed),
                        PossessionNeedResponse.class);
    }

    public void upvote(String id, String authorization) {
        PossessionNeed possessionNeed = possessionNeedRepository.findById(id).orElseThrow(
                        () -> new DocumentNotFoundException("Need not found"));
        possessionNeed.upvote(userService.getUser(authorization).getId());
        possessionNeedRepository.save(possessionNeed);
    }

    public void downvote(String id, String authorization) {
        PossessionNeed possessionNeed = possessionNeedRepository.findById(id).orElseThrow(
                        () -> new DocumentNotFoundException("Need not found"));
        possessionNeed.downvote(userService.getUser(authorization).getId());
        possessionNeedRepository.save(possessionNeed);
    }
}
