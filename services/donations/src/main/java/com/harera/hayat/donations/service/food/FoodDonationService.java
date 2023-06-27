package com.harera.hayat.donations.service.food;

import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.DonationStatus;
import com.harera.hayat.donations.model.ai.Prediction;
import com.harera.hayat.donations.model.food.FoodDonation;
import com.harera.hayat.donations.model.food.FoodDonationRequest;
import com.harera.hayat.donations.model.food.FoodDonationResponse;
import com.harera.hayat.donations.model.food.FoodDonationUpdateRequest;
import com.harera.hayat.donations.repository.food.FoodDonationRepository;
import com.harera.hayat.donations.service.BaseService;
import com.harera.hayat.donations.service.DonationNotificationsService;
import com.harera.hayat.donations.service.ai.PredictionService;
import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.model.food.FoodCategory;
import com.harera.hayat.framework.model.food.FoodUnit;
import com.harera.hayat.framework.repository.city.CityRepository;
import com.harera.hayat.framework.repository.food.FoodCategoryRepository;
import com.harera.hayat.framework.repository.food.FoodUnitRepository;
import com.harera.hayat.framework.service.file.CloudFileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.harera.hayat.framework.util.FileUtils.convertMultiPartToFile;
import static com.harera.hayat.framework.util.ObjectMapperUtils.mapAll;

@Service
public class FoodDonationService extends BaseService {

    private final FoodDonationValidation foodDonationValidation;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;
    private final FoodUnitRepository foodUnitRepository;
    private final FoodDonationRepository foodDonationRepository;
    private final int foodDonationExpirationDays;
    private final FoodCategoryRepository foodCategoryRepository;
    private final CloudFileService cloudFileService;
    private final DonationNotificationsService donationNotificationsService;
    private final PredictionService predictionService;

    public FoodDonationService(FoodDonationValidation donationValidation,
                    CityRepository cityRepository, ModelMapper modelMapper,
                    FoodUnitRepository foodUnitRepository,
                    FoodDonationRepository foodDonationRepository,
                    @Value("${donation.food.expiration_in_days}") int foodDonationExpirationDays,
                    FoodCategoryRepository foodCategoryRepository,
                    CloudFileService cloudFileService,
                    DonationNotificationsService donationNotificationsService,
                    PredictionService predictionService) {
        this.foodDonationValidation = donationValidation;
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.foodUnitRepository = foodUnitRepository;
        this.foodDonationRepository = foodDonationRepository;
        this.foodDonationExpirationDays = foodDonationExpirationDays;
        this.foodCategoryRepository = foodCategoryRepository;
        this.cloudFileService = cloudFileService;
        this.donationNotificationsService = donationNotificationsService;
        this.predictionService = predictionService;
    }

    public FoodDonationResponse create(FoodDonationRequest foodDonationRequest,
                    String authorization) {
        foodDonationValidation.validateCreate(foodDonationRequest);

        FoodDonation foodDonation =
                        modelMapper.map(foodDonationRequest, FoodDonation.class);
        foodDonation.setStatus(DonationStatus.PENDING);
        foodDonation.setCategory(DonationCategory.FOOD);
        foodDonation.setDonationDate(OffsetDateTime.now());
        foodDonation.setDonationExpirationDate(getDonationExpirationDate());
        foodDonation.setCity(getCity(foodDonationRequest.getCityId()));
        foodDonation.setUser(getUser(authorization));
        foodDonation.setFoodUnit(getUnit(foodDonationRequest.getFoodUnitId()));

        donationNotificationsService.notifyProcessingDonation(foodDonation);
        reviewDonation(foodDonation);

        foodDonationRepository.save(foodDonation);
        return modelMapper.map(foodDonation, FoodDonationResponse.class);
    }

    private void reviewDonation(FoodDonation foodDonation) {
        Prediction prediction = predictionService.predict(
                        foodDonation.getTitle() + " " + foodDonation.getDescription());
        if (Objects.equals(prediction.getLabel(), "FOOD")
                        && prediction.getCertainty() > 0.5) {
            foodDonation.setStatus(DonationStatus.ACTIVE);
        } else {
            foodDonation.setStatus(DonationStatus.REJECTED);
        }
        foodDonationRepository.save(foodDonation);
    }

    public FoodDonationResponse update(Long id, FoodDonationUpdateRequest request) {
        foodDonationValidation.validateUpdate(id, request);

        FoodDonation foodDonation = foodDonationRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(FoodDonation.class, id));

        modelMapper.map(request, foodDonation);

        foodDonation.setCity(getCity(request.getCityId()));
        foodDonation.setFoodUnit(getUnit(request.getFoodUnitId()));
        foodDonation.setFoodCategory(getCategory(request.getFoodCategoryId()));

        foodDonationRepository.save(foodDonation);

        return modelMapper.map(foodDonation, FoodDonationResponse.class);
    }

    private OffsetDateTime getDonationExpirationDate() {
        return OffsetDateTime.now().plusDays(foodDonationExpirationDays);
    }

    private City getCity(Long cityId) {
        return cityRepository.findById(cityId).orElseThrow(
                        () -> new EntityNotFoundException(City.class, cityId));
    }

    private FoodUnit getUnit(Long id) {
        return foodUnitRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(FoodUnit.class, id));
    }

    private FoodCategory getCategory(Long id) {
        if (id != null) {
            return foodCategoryRepository.findById(id).orElseThrow(
                            () -> new EntityNotFoundException(FoodCategory.class, id));
        }
        return null;
    }

    public List<FoodDonationResponse> list(int pageSize, int pageNumber) {
        List<FoodDonation> foodDonationList = foodDonationRepository
                        .findAll(PageRequest.of(pageNumber, pageSize)).getContent();
        List<FoodDonationResponse> response = new LinkedList<>();
        for (FoodDonation foodDonation : foodDonationList) {
            FoodDonationResponse foodDonationResponse =
                            modelMapper.map(foodDonation, FoodDonationResponse.class);
            response.add(foodDonationResponse);
        }
        return response;
    }

    public FoodDonationResponse get(Long id) {
        FoodDonation foodDonation = foodDonationRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(FoodDonation.class, id));
        return modelMapper.map(foodDonation, FoodDonationResponse.class);
    }

    public FoodDonationResponse updateImage(Long id, MultipartFile file) {
        FoodDonation foodDonation = foodDonationRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(FoodDonation.class, id));

        String imageUrl = cloudFileService.uploadFile(convertMultiPartToFile(file));
        if (foodDonation.getImageUrl() == null) {
            foodDonation.setImageUrl(imageUrl);
        } else {
            cloudFileService.deleteFile(foodDonation.getImageUrl());
            foodDonation.setImageUrl(imageUrl);
        }

        foodDonationRepository.save(foodDonation);
        return modelMapper.map(foodDonation, FoodDonationResponse.class);
    }

    public void upvote(Long id, String authorization) {
        FoodDonation foodDonation = foodDonationRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(FoodDonation.class, id));
        foodDonation.upvote(getUser(authorization));
        foodDonationRepository.save(foodDonation);
    }

    public void downvote(Long id, String authorization) {
        FoodDonation foodDonation = foodDonationRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(FoodDonation.class, id));
        foodDonation.downvote(getUser(authorization));
        foodDonationRepository.save(foodDonation);
    }

    public List<FoodDonationResponse> search(String query, Integer page) {
        page = Integer.max(page, 1) - 1;
        List<FoodDonation> search = foodDonationRepository.search(query,
                        Pageable.ofSize(16).withPage(page));
        return mapAll(search, FoodDonationResponse.class);
    }
}
