package com.harera.hayat.donations.service.property;

import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.DonationState;
import com.harera.hayat.donations.model.property.PropertyDonation;
import com.harera.hayat.donations.model.property.PropertyDonationRequest;
import com.harera.hayat.donations.model.property.PropertyDonationResponse;
import com.harera.hayat.donations.model.property.PropertyDonationUpdateRequest;
import com.harera.hayat.donations.repository.property.PropertyDonationRepository;
import com.harera.hayat.donations.service.BaseService;
import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.repository.city.CityRepository;
import com.harera.hayat.framework.util.ErrorCode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class PropertyDonationService implements BaseService {

    @Value("${donation.property.page_size:10}")
    private int pageSize;
    @Value("${donation.property.expirations_days:45}")
    private int expirationDays;

    private final PropertyDonationRepository propertyDonationRepository;
    private final PropertyDonationValidation donationValidation;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    public PropertyDonationService(PropertyDonationRepository propertyDonationRepository,
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

        propertyDonation.setStatus(DonationState.PENDING);
        propertyDonation.setCategory(DonationCategory.PROPERTY);
        propertyDonation.setDonationDate(OffsetDateTime.now());
        propertyDonation.setDonationExpirationDate(OffsetDateTime.now().plusDays(expirationDays));

        assignCity(propertyDonation, propertyDonationRequest.getCityId());
        // TODO: set user

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

    public PropertyDonationResponse update(Long id,
                    PropertyDonationUpdateRequest propertyDonationUpdateRequest) {
        donationValidation.validateUpdate(id, propertyDonationUpdateRequest);
        PropertyDonation propertyDonation =
                        propertyDonationRepository.findById(id).orElseThrow();

        modelMapper.map(propertyDonationUpdateRequest, propertyDonation);
        propertyDonationRepository.save(propertyDonation);
        return modelMapper.map(propertyDonation, PropertyDonationResponse.class);
    }

    public List<PropertyDonationResponse> list(int page) {
        return propertyDonationRepository
                        .findAll(Pageable.ofSize(pageSize).withPage(page))
                        .map(propertyDonation -> modelMapper.map(propertyDonation,
                                        PropertyDonationResponse.class))
                        .toList();
    }
}
