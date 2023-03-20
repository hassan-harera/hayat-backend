package com.harera.hayat.donations.service.clothing;

import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.DonationStatus;
import com.harera.hayat.donations.model.clothing.ClothingDonation;
import com.harera.hayat.donations.model.clothing.ClothingDonationRequest;
import com.harera.hayat.donations.model.clothing.ClothingDonationResponse;
import com.harera.hayat.donations.model.clothing.ClothingDonationUpdateRequest;
import com.harera.hayat.donations.repository.clothing.ClothingDonationRepository;
import com.harera.hayat.donations.service.BaseService;
import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.service.city.CityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class ClothingDonationService implements BaseService {

    @Value("${donation.clothing.expiration_in_days:45}")
    private int expirationDays;

    private final ClothingDonationValidation clothingDonationValidation;
    private final CityService citService;
    private final ModelMapper modelMapper;
    private final ClothingDonationRepository clothingDonationRepository;

    public ClothingDonationService(ClothingDonationValidation donationValidation,
                    ModelMapper modelMapper, CityService citService,
                    ClothingDonationRepository clothingDonationRepository) {
        this.clothingDonationValidation = donationValidation;
        this.modelMapper = modelMapper;
        this.citService = citService;
        this.clothingDonationRepository = clothingDonationRepository;
    }

    public ClothingDonationResponse create(
                    ClothingDonationRequest clothingDonationRequest) {
        clothingDonationValidation.validateCreate(clothingDonationRequest);

        ClothingDonation clothingDonation =
                        modelMapper.map(clothingDonationRequest, ClothingDonation.class);

        clothingDonation.setCategory(DonationCategory.CLOTHING);
        clothingDonation.setStatus(DonationStatus.PENDING);
        clothingDonation.setDonationDate(OffsetDateTime.now());
        clothingDonation.setDonationExpirationDate(
                        OffsetDateTime.now().plusDays(expirationDays));

        // TODO: set user
        clothingDonation.setCity(citService.getCity(clothingDonationRequest.getCityId()));
        // TODO: send request to ai for processing

        clothingDonationRepository.save(clothingDonation);
        return modelMapper.map(clothingDonation, ClothingDonationResponse.class);
    }

    public ClothingDonationResponse update(Long id,
                    ClothingDonationUpdateRequest request) {
        clothingDonationValidation.validateUpdate(id, request);

        ClothingDonation clothingDonation =
                        clothingDonationRepository.findById(id).orElseThrow();
        modelMapper.map(request, clothingDonation);

        // TODO: set user
        clothingDonation.setCity(citService.getCity(request.getCityId()));

        clothingDonationRepository.save(clothingDonation);
        return modelMapper.map(clothingDonation, ClothingDonationResponse.class);
    }

    public List<ClothingDonationResponse> list(int pageSize, int pageNumber) {
        List<ClothingDonation> clothingDonationList = clothingDonationRepository
                        .findAll(PageRequest.of(pageNumber, pageSize)).getContent();
        List<ClothingDonationResponse> response = new LinkedList<>();
        for (ClothingDonation clothingDonation : clothingDonationList) {
            ClothingDonationResponse clothingDonationResponse = modelMapper
                            .map(clothingDonation, ClothingDonationResponse.class);
            response.add(clothingDonationResponse);
        }
        return response;
    }

    public ClothingDonationResponse get(Long id) {
        ClothingDonation clothingDonation = clothingDonationRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                        ClothingDonation.class, id));
        return modelMapper.map(clothingDonation, ClothingDonationResponse.class);
    }
}
