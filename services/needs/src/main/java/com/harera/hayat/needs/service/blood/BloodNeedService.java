package com.harera.hayat.needs.service.blood;

import com.harera.hayat.framework.exception.DocumentNotFoundException;
import com.harera.hayat.framework.model.user.BaseUserDto;
import com.harera.hayat.framework.service.city.CityService;
import com.harera.hayat.needs.model.NeedCategory;
import com.harera.hayat.needs.model.blood.BloodNeed;
import com.harera.hayat.needs.model.blood.BloodNeedRequest;
import com.harera.hayat.needs.model.blood.BloodNeedResponse;
import com.harera.hayat.needs.repository.blood.BloodNeedRepository;
import com.harera.hayat.needs.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BloodNeedService {

    private final ModelMapper modelMapper;
    private final BloodNeedRepository bloodNeedRepository;
    private final BloodNeedValidation bloodNeedValidation;
    private final CityService cityService;
    private final UserService userService;

    @Autowired
    public BloodNeedService(ModelMapper modelMapper,
                    BloodNeedRepository bloodNeedRepository,
                    BloodNeedValidation bloodNeedValidation, CityService cityService,
                    UserService userService) {
        this.modelMapper = modelMapper;
        this.bloodNeedRepository = bloodNeedRepository;
        this.bloodNeedValidation = bloodNeedValidation;
        this.cityService = cityService;
        this.userService = userService;
    }

    public BloodNeedResponse create(BloodNeedRequest bloodNeedRequest,
                    String authorization) {
        bloodNeedValidation.validate(bloodNeedRequest);

        BloodNeed bloodNeed = modelMapper.map(bloodNeedRequest, BloodNeed.class);
        bloodNeed.setNeedDate(LocalDateTime.now());
        bloodNeed.setNeedExpirationDate(LocalDateTime.now().plusDays(15));
        bloodNeed.setCity(cityService.get(bloodNeedRequest.getCityId()));
        bloodNeed.setCategory(NeedCategory.BLOOD);

        bloodNeed.setUser(modelMapper.map(userService.getUser(authorization),
                        BaseUserDto.class));

        bloodNeedRepository.save(bloodNeed);
        return modelMapper.map(bloodNeed, BloodNeedResponse.class);
    }

    public void upvote(String id, String authorization) {
        BloodNeed bloodNeed = bloodNeedRepository.findById(id).orElseThrow(
                        () -> new DocumentNotFoundException("Blood Need not found"));
        bloodNeed.upvote(userService.getUser(authorization).getId());
        bloodNeedRepository.save(bloodNeed);
    }

    public void downvote(String id, String authorization) {
        BloodNeed bloodNeed = bloodNeedRepository.findById(id).orElseThrow(
                        () -> new DocumentNotFoundException("Blood Need not found"));
        bloodNeed.downvote(userService.getUser(authorization).getId());
        bloodNeedRepository.save(bloodNeed);
    }
}
