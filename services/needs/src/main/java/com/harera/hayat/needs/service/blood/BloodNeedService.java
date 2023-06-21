package com.harera.hayat.needs.service.blood;

import com.harera.hayat.framework.exception.DocumentNotFoundException;
import com.harera.hayat.framework.model.user.BaseUserDto;
import com.harera.hayat.framework.service.city.CityService;
import com.harera.hayat.framework.service.file.CloudFileService;
import com.harera.hayat.needs.model.NeedCategory;
import com.harera.hayat.needs.model.NeedStatus;
import com.harera.hayat.needs.model.blood.BloodNeed;
import com.harera.hayat.needs.model.blood.BloodNeedRequest;
import com.harera.hayat.needs.model.blood.BloodNeedResponse;
import com.harera.hayat.needs.model.blood.BloodNeedUpdateRequest;
import com.harera.hayat.needs.repository.blood.BloodNeedRepository;
import com.harera.hayat.needs.service.NeedNotificationsService;
import com.harera.hayat.needs.service.NeedValidation;
import com.harera.hayat.needs.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import static com.harera.hayat.framework.util.FileUtils.convertMultiPartToFile;
import static com.harera.hayat.framework.util.ObjectMapperUtils.mapAll;
import static com.harera.hayat.framework.util.PageableUtils.of;

@Service
public class BloodNeedService {

    private final ModelMapper modelMapper;
    private final NeedValidation needValidation;
    private final BloodNeedRepository bloodNeedRepository;
    private final BloodNeedValidation bloodNeedValidation;
    private final CityService cityService;
    private final UserService userService;
    private final CloudFileService cloudFileService;
    private final NeedNotificationsService needNotificationsService;

    @Autowired
    public BloodNeedService(ModelMapper modelMapper, NeedValidation needValidation,
                    BloodNeedRepository bloodNeedRepository,
                    BloodNeedValidation bloodNeedValidation, CityService cityService,
                    UserService userService, CloudFileService cloudFileService,
                    NeedNotificationsService needNotificationsService) {
        this.modelMapper = modelMapper;
        this.needValidation = needValidation;
        this.bloodNeedRepository = bloodNeedRepository;
        this.bloodNeedValidation = bloodNeedValidation;
        this.cityService = cityService;
        this.userService = userService;
        this.cloudFileService = cloudFileService;
        this.needNotificationsService = needNotificationsService;
    }

    public BloodNeedResponse create(BloodNeedRequest bloodNeedRequest,
                    String authorization) {
        bloodNeedValidation.validate(bloodNeedRequest);

        BloodNeed bloodNeed = modelMapper.map(bloodNeedRequest, BloodNeed.class);
        bloodNeed.setNeedDate(LocalDateTime.now());
        bloodNeed.setNeedExpirationDate(LocalDateTime.now().plusDays(15));
        bloodNeed.setCity(cityService.get(bloodNeedRequest.getCityId()));
        bloodNeed.setStatus(NeedStatus.PENDING);
        bloodNeed.setCategory(NeedCategory.BLOOD);
        bloodNeed.setUser(modelMapper.map(userService.getUser(authorization),
                        BaseUserDto.class));

        needNotificationsService.notifyProcessingNeed(bloodNeed);

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

    public BloodNeedResponse get(String id) {
        return modelMapper.map(
                        bloodNeedRepository.findById(id)
                                        .orElseThrow(() -> new DocumentNotFoundException(
                                                        BloodNeed.class, id)),
                        BloodNeedResponse.class);
    }

    public List<BloodNeedResponse> list(int page) {
        page = Integer.max(page, 1) - 1;
        List<BloodNeed> bloodNeeds = bloodNeedRepository
                        .findAll(Pageable.ofSize(16).withPage(page)).getContent();
        return mapAll(bloodNeeds, BloodNeedResponse.class);
    }

    public BloodNeedResponse update(String id, BloodNeedUpdateRequest request) {
        needValidation.validateUpdate(request);

        BloodNeed bloodNeed = bloodNeedRepository.findById(id).orElseThrow(
                        () -> new DocumentNotFoundException(BloodNeed.class, id));
        modelMapper.map(request, bloodNeed);

        bloodNeed.setCategory(NeedCategory.MEDICINE);
        bloodNeed.setStatus(NeedStatus.PENDING);
        bloodNeed.setCity(cityService.get(request.getCityId()));

        // TODO: 28/02/23 send request to ai model

        bloodNeedRepository.save(bloodNeed);
        return modelMapper.map(bloodNeed, BloodNeedResponse.class);
    }

    public List<BloodNeedResponse> search(String query, int page) {
        return search(query, page, 16);
    }

    public BloodNeedResponse updateImage(String id, MultipartFile file) {
        BloodNeed bloodNeed = bloodNeedRepository.findById(id).orElseThrow(
                        () -> new DocumentNotFoundException(BloodNeed.class, id));

        String imageUrl = cloudFileService.uploadFile(convertMultiPartToFile(file));
        if (bloodNeed.getImageUrl() == null) {
            bloodNeed.setImageUrl(imageUrl);
        } else {
            cloudFileService.deleteFile(bloodNeed.getImageUrl());
            bloodNeed.setImageUrl(imageUrl);
        }

        bloodNeedRepository.save(bloodNeed);
        return modelMapper.map(bloodNeed, BloodNeedResponse.class);
    }

    public List<BloodNeedResponse> search(String query, int page, int size) {
        page = Integer.max(page, 1) - 1;
        List<BloodNeed> needList = bloodNeedRepository.search(query, NeedStatus.ACTIVE,
                        of(page, size));
        return mapAll(needList, BloodNeedResponse.class);
    }
}
