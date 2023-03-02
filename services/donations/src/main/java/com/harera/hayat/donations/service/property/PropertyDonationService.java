package com.harera.hayat.donations.service.property;

import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.DonationState;
import com.harera.hayat.donations.model.property.PropertyDonation;
import com.harera.hayat.donations.model.property.PropertyDonationRequest;
import com.harera.hayat.donations.model.property.PropertyDonationResponse;
import com.harera.hayat.donations.repository.property.PropertyDonationRepository;
import com.harera.hayat.donations.service.BaseService;
import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.repository.city.CityRepository;
import com.harera.hayat.framework.util.ErrorCode;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PropertyDonationService implements BaseService {

    private final PropertyDonationRepository propertyDonationRepository;
    private final PropertyDonationValidation donationValidation;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    public PropertyDonationService(
                    PropertyDonationRepository propertyDonationRepository,
                    PropertyDonationValidation donationValidation,
                    CityRepository cityRepository, ModelMapper modelMapper) {
        this.propertyDonationRepository = propertyDonationRepository;
        this.donationValidation = donationValidation;
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    public PropertyDonationResponse create(
                    PropertyDonationRequest propertyDonationRequest) {
        donationValidation.validateCreate(propertyDonationRequest);
        PropertyDonation propertyDonation =
                        modelMapper.map(propertyDonationRequest, PropertyDonation.class);
        assignCity(propertyDonation, propertyDonationRequest.getCityId());
        propertyDonation.setUser(getRequestUser());
        propertyDonation.setStatus(DonationState.PENDING);
        propertyDonation.setCategory(DonationCategory.PROPERTY);
        propertyDonationRepository.save(propertyDonation);
        return modelMapper.map(propertyDonation, PropertyDonationResponse.class);
    }

    private void assignCity(PropertyDonation donation, Long cityId) {
        City city = cityRepository.findById(cityId)
                        .orElseThrow(() -> new EntityNotFoundException(City.class, cityId,
                                        ErrorCode.NOT_FOUND_DONATION_CITY));
        donation.setCity(city);
    }

    public PropertyDonationResponse get(Long propertyDonationId) {
        PropertyDonation propertyDonation = propertyDonationRepository
                        .findById(propertyDonationId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                        PropertyDonation.class, propertyDonationId,
                                        ErrorCode.NOT_FOUND_PROPERTY_DONATION));
        return modelMapper.map(propertyDonation, PropertyDonationResponse.class);
    }
}
