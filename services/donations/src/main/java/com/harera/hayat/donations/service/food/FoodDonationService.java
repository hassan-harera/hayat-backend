package com.harera.hayat.donations.service.food;

import com.harera.hayat.donations.model.Donation;
import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.food.*;
import com.harera.hayat.donations.repository.DonationRepository;
import com.harera.hayat.donations.repository.food.FoodDonationRepository;
import com.harera.hayat.donations.repository.food.FoodUnitRepository;
import com.harera.hayat.donations.service.BaseService;
import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.model.user.User;
import com.harera.hayat.framework.repository.city.CityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class FoodDonationService implements BaseService {

    private final DonationRepository donationRepository;
    private final FoodDonationValidation foodDonationValidation;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;
    private final FoodUnitRepository foodUnitRepository;
    private final FoodDonationRepository foodDonationRepository;
    private final int foodDonationExpirationDays;

    public FoodDonationService(DonationRepository donationRepository,
                    FoodDonationValidation donationValidation,
                    CityRepository cityRepository, ModelMapper modelMapper,
                    FoodUnitRepository foodUnitRepository,
                    FoodDonationRepository foodDonationRepository,
                    @Value("${donation.food.expiration_in_days}") int foodDonationExpirationDays) {
        this.donationRepository = donationRepository;
        this.foodDonationValidation = donationValidation;
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.foodUnitRepository = foodUnitRepository;
        this.foodDonationRepository = foodDonationRepository;
        this.foodDonationExpirationDays = foodDonationExpirationDays;
    }

    public FoodDonationResponse create(FoodDonationRequest foodDonationRequest) {
        foodDonationValidation.validateCreate(foodDonationRequest);

        Donation donation = modelMapper.map(foodDonationRequest, Donation.class);
        donation.setCategory(DonationCategory.FOOD);
        donation.setDonationDate(OffsetDateTime.now());
        donation.setDonationExpirationDate(getDonationExpirationDate());
        donation.setCity(getCity(foodDonationRequest.getCityId()));
        donation.setUser(getRequestUser());
        donationRepository.save(donation);

        FoodDonation foodDonation =
                        modelMapper.map(foodDonationRequest, FoodDonation.class);
        foodDonation.setUnit(getUnit(foodDonationRequest.getUnitId()));
        foodDonation.setDonation(donation);
        foodDonationRepository.save(foodDonation);

        FoodDonationResponse response =
                        modelMapper.map(foodDonation, FoodDonationResponse.class);
        modelMapper.map(donation, response);
        return response;
    }

    public FoodDonationResponse update(Long id, FoodDonationUpdateRequest request) {
        foodDonationValidation.validateUpdate(id, request);

        FoodDonation foodDonation = foodDonationRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(FoodDonation.class, id));

        Donation donation = foodDonation.getDonation();
        modelMapper.map(request, donation);
        donation.setCity(getCity(request.getCityId()));
        donation.setUser(getRequestUser());
        donationRepository.save(donation);

        modelMapper.map(request, foodDonation);
        foodDonation.setUnit(getUnit(request.getUnitId()));
        foodDonation.setDonation(donation);
        foodDonationRepository.save(foodDonation);

        FoodDonationResponse response =
                        modelMapper.map(donation, FoodDonationResponse.class);
        modelMapper.map(foodDonation, response);
        response.setId(foodDonation.getId());
        return response;
    }

    private OffsetDateTime getDonationExpirationDate() {
        return OffsetDateTime.now().plusDays(foodDonationExpirationDays);
    }

    private City getCity(Long cityId) {
        return cityRepository.findById(cityId).orElseThrow(
                        () -> new EntityNotFoundException(City.class, cityId));
    }

    private FoodUnit getUnit(Long unitId) {
        return foodUnitRepository.findById(unitId).orElseThrow(
                        () -> new EntityNotFoundException(FoodUnit.class, unitId));

    }

    public List<FoodDonationResponse> list(int pageSize, int pageNumber) {
        List<FoodDonation> foodDonationList = foodDonationRepository
                        .findAll(PageRequest.of(pageNumber, pageSize)).getContent();
        List<FoodDonationResponse> response = new LinkedList<>();
        for (FoodDonation foodDonation : foodDonationList) {
            FoodDonationResponse foodDonationResponse = modelMapper
                            .map(foodDonation.getDonation(), FoodDonationResponse.class);
            modelMapper.map(foodDonation, foodDonationResponse);
            response.add(foodDonationResponse);
        }
        return response;
    }

    public FoodDonationResponse get(Long id) {
        FoodDonation foodDonation = foodDonationRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(FoodDonation.class, id));
        FoodDonationResponse response = modelMapper.map(foodDonation.getDonation(),
                        FoodDonationResponse.class);
        modelMapper.map(foodDonation, response);
        return response;
    }
}
