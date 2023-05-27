package com.harera.hayat.donations.service;

import com.harera.hayat.donations.model.Donation;
import com.harera.hayat.donations.model.DonationResponse;
import com.harera.hayat.donations.model.food.FoodDonation;
import com.harera.hayat.donations.repository.DonationRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.harera.hayat.framework.util.ObjectMapperUtils.mapAll;

@Service
public class DonationsService {

    private final DonationRepository donationRepository;

    public DonationsService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public List<DonationResponse> search(String query, int page) {
        page = Integer.max(page, 1) - 1;
        List<Donation> search = donationRepository.search(query,
                        Pageable.ofSize(16).withPage(page));
        return mapAll(search, DonationResponse.class);
    }
}
