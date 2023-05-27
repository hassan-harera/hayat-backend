package com.harera.hayat.donations.stubs.property;

import com.harera.hayat.donations.model.CommunicationMethod;
import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.DonationStatus;
import com.harera.hayat.donations.model.property.PropertyDonation;
import com.harera.hayat.donations.repository.property.PropertyDonationRepository;
import com.harera.hayat.framework.model.city.City;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PropertyDonationStubs {

    private final PropertyDonationRepository propertyDonationRepository;

    public PropertyDonation create(String title, String description,
                    LocalDateTime donationDate, LocalDateTime donationExpirationDate,
                    DonationCategory category, DonationStatus status,
                    CommunicationMethod communicationMethod, int rooms, int bathrooms,
                    int kitchens, City city, int peopleCapacity,
                    LocalDateTime availableFrom, LocalDateTime availableTo) {
        PropertyDonation propertyDonation = new PropertyDonation();
        propertyDonation.setId(0L);
        propertyDonation.setTitle(title);
        propertyDonation.setDescription(description);
        propertyDonation.setDonationDate(donationDate);
        propertyDonation.setDonationExpirationDate(donationExpirationDate);
        propertyDonation.setCategory(category);
        propertyDonation.setStatus(status);
        propertyDonation.setCommunicationMethod(communicationMethod);
        propertyDonation.setCity(city);
        propertyDonation.setBathrooms(bathrooms);
        propertyDonation.setKitchens(kitchens);
        propertyDonation.setRooms(rooms);
        propertyDonation.setPeopleCapacity(peopleCapacity);
        propertyDonation.setAvailableFrom(availableFrom);
        propertyDonation.setAvailableTo(availableTo);
        return propertyDonation;
    }

    public PropertyDonation insert(String title, String description,
                    LocalDateTime donationDate, LocalDateTime donationExpirationDate,
                    DonationCategory category, DonationStatus status,
                    CommunicationMethod communicationMethod, int rooms, int bathrooms,
                    int kitchens, City city, int peopleCapacity,
                    LocalDateTime availableFrom, LocalDateTime availableTo) {
        PropertyDonation propertyDonation = create(title, description, donationDate,
                        donationExpirationDate, category, status, communicationMethod,
                        rooms, bathrooms, kitchens, city, peopleCapacity, availableFrom,
                        availableTo);
        return propertyDonationRepository.save(propertyDonation);
    }

    public PropertyDonation get(Long id) {
        return propertyDonationRepository.findById(id).orElse(null);
    }
}
