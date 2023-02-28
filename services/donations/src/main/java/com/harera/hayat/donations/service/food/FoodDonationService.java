package com.harera.hayat.donations.service.food;

import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.food.FoodDonation;
import com.harera.hayat.donations.model.food.FoodDonationRequest;
import com.harera.hayat.donations.model.food.FoodDonationResponse;
import com.harera.hayat.donations.model.food.FoodDonationUpdateRequest;
import com.harera.hayat.donations.repository.food.FoodDonationRepository;
import com.harera.hayat.donations.service.BaseService;
import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.model.food.FoodCategory;
import com.harera.hayat.framework.model.food.FoodUnit;
import com.harera.hayat.framework.repository.city.CityRepository;
import com.harera.hayat.framework.repository.food.FoodCategoryRepository;
import com.harera.hayat.framework.repository.food.FoodUnitRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class FoodDonationService implements BaseService {

    private final FoodDonationValidation foodDonationValidation;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;
    private final FoodUnitRepository foodUnitRepository;
    private final FoodDonationRepository foodDonationRepository;
    private final int foodDonationExpirationDays;
    private final FoodCategoryRepository foodCategoryRepository;

    public FoodDonationService(FoodDonationValidation donationValidation,
                    CityRepository cityRepository, ModelMapper modelMapper,
                    FoodUnitRepository foodUnitRepository,
                    FoodDonationRepository foodDonationRepository,
                    @Value("${donation.food.expiration_in_days}") int foodDonationExpirationDays,
                               FoodCategoryRepository foodCategoryRepository) {
        this.foodDonationValidation = donationValidation;
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.foodUnitRepository = foodUnitRepository;
        this.foodDonationRepository = foodDonationRepository;
        this.foodDonationExpirationDays = foodDonationExpirationDays;
        this.foodCategoryRepository = foodCategoryRepository;
    }

    public FoodDonationResponse create(FoodDonationRequest foodDonationRequest) {
        foodDonationValidation.validateCreate(foodDonationRequest);

        FoodDonation foodDonation =
                        modelMapper.map(foodDonationRequest, FoodDonation.class);
        foodDonation.setCategory(DonationCategory.FOOD);
        foodDonation.setDonationDate(OffsetDateTime.now());
        foodDonation.setDonationExpirationDate(getDonationExpirationDate());
        foodDonation.setCity(getCity(foodDonationRequest.getCityId()));
        // TODO: connect to keycloak
        // donation.setUser(getRequestUser());
        foodDonation.setUnit(getUnit(foodDonationRequest.getUnitId()));

        foodDonationRepository.save(foodDonation);
        return modelMapper.map(foodDonation, FoodDonationResponse.class);
    }

    public FoodDonationResponse update(Long id, FoodDonationUpdateRequest request) {
        foodDonationValidation.validateUpdate(id, request);

        FoodDonation foodDonation = foodDonationRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(FoodDonation.class, id));

        modelMapper.map(request, foodDonation);

        foodDonation.setCity(getCity(request.getCityId()));
        // TODO: connect to keycloak
        // foodDonation.setUser(getRequestUser());
        foodDonation.setUnit(getUnit(request.getUnitId()));
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
}
