package com.harera.hayat.donations.service;

import com.harera.hayat.donations.model.Donation;
import com.harera.hayat.donations.model.DonationDto;
import com.harera.hayat.donations.model.DonationResponse;
import com.harera.hayat.donations.model.food.FoodDonationResponse;
import com.harera.hayat.donations.repository.DonationRepository;
import com.harera.hayat.donations.repository.food.FoodDonationRepository;
import com.harera.hayat.donations.service.food.FoodDonationService;
import com.harera.hayat.donations.service.medicine.MedicineDonationService;
import com.harera.hayat.framework.repository.city.CityRepository;
import com.harera.hayat.framework.util.PageableUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final FoodDonationRepository foodDonationRepository;
    private final DonationValidation donationValidation;
    private final FoodDonationService foodDonationService;
    private final MedicineDonationService medicinDonationService;
    private final ModelMapper modelMapper;

    public DonationService(DonationRepository donationRepository,
                    FoodDonationRepository foodDonationRepository,
                    DonationValidation donationValidation, CityRepository cityRepository,
                    FoodDonationService foodDonationService,
                    MedicineDonationService medicinDonationService,
                    ModelMapper modelMapper) {
        this.donationRepository = donationRepository;
        this.foodDonationRepository = foodDonationRepository;
        this.donationValidation = donationValidation;
        this.foodDonationService = foodDonationService;
        this.medicinDonationService = medicinDonationService;
        this.modelMapper = modelMapper;
    }

    public List<DonationResponse> list(Integer page, Integer size, String query,
                                       String category) {
        Pageable pageable = PageableUtils.of(page, size);
        Page<Donation> all = donationRepository.findAll(pageable);
        List<DonationResponse> donationResponses = all.map(
                        donation -> modelMapper.map(donation, DonationResponse.class))
                        .toList();
        return donationResponses;
    }



    public List<FoodDonationResponse> listFoodDonations() {
        List<FoodDonationResponse> foodDonationList = new LinkedList<>();
        foodDonationRepository.findAll().forEach(foodDonation -> {
            FoodDonationResponse foodDonationResponse =
                            modelMapper.map(foodDonation, FoodDonationResponse.class);
            DonationDto donationDto = modelMapper.map(foodDonation.getDonation(),
                            DonationDto.class);
            modelMapper.map(donationDto, foodDonationResponse);
            foodDonationList.add(foodDonationResponse);
        });
        return foodDonationList;
    }

    public void deactivateExpiredDonations() {

    }
}
