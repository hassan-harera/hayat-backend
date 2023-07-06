package com.harera.hayat.donations.service;

import com.harera.hayat.donations.model.Donation;
import com.harera.hayat.donations.model.DonationResponse;
import com.harera.hayat.donations.repository.DonationRepository;
import com.harera.hayat.donations.service.book.BookDonationService;
import com.harera.hayat.donations.service.clothing.ClothingDonationService;
import com.harera.hayat.donations.service.food.FoodDonationService;
import com.harera.hayat.donations.service.medicine.MedicineDonationService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.harera.hayat.framework.util.ObjectMapperUtils.mapAll;

@Service
public class DonationsService {

    private final DonationRepository donationRepository;
    private final MedicineDonationService medicineDonationService;
    private final FoodDonationService foodDonationService;
    private final BookDonationService bookDonationService;
    private final ClothingDonationService clothingDonationService;

    public DonationsService(DonationRepository donationRepository,
                    MedicineDonationService medicineDonationService,
                    FoodDonationService foodDonationService,
                    BookDonationService bookDonationService,
                    ClothingDonationService clothingDonationService) {
        this.donationRepository = donationRepository;
        this.medicineDonationService = medicineDonationService;
        this.foodDonationService = foodDonationService;
        this.bookDonationService = bookDonationService;
        this.clothingDonationService = clothingDonationService;
    }

    public List<DonationResponse> search(String query, int page) {
        page = Integer.max(page, 1) - 1;
        List<Donation> search = donationRepository.search(query,
                        Pageable.ofSize(16).withPage(page));
        return mapAll(search, DonationResponse.class);
    }

    public void receive(Long id) {
        donationRepository.findById(id).ifPresent(this::updateDonation);
    }

    private void updateDonation(Donation donation) {
        switch (donation.getCategory()) {
            case MEDICINE:
                medicineDonationService.receive(donation.getId());
                break;
            case FOOD:
                foodDonationService.receive(donation.getId());
                break;
            case BOOKS:
                bookDonationService.receive(donation.getId());
                break;
            case CLOTHING:
                clothingDonationService.receive(donation.getId());
                break;
        }
    }
}
