package com.harera.hayat.donations.stubs.food;

import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.DonationStatus;
import com.harera.hayat.donations.model.food.FoodDonation;
import com.harera.hayat.donations.repository.food.FoodDonationRepository;
import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.model.food.FoodUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FoodDonationStubs {

    private final FoodDonationRepository foodDonationRepository;

    public FoodDonation create(FoodUnit unit, Float amount,
                    OffsetDateTime foodExpirationDate, String title,
                    DonationCategory category, String description, City city,
                    DonationStatus state) {
        FoodDonation foodDonation = new FoodDonation();
        foodDonation.setId(0L);
        foodDonation.setFoodUnit(unit);
        foodDonation.setQuantity(amount);
        foodDonation.setFoodExpirationDate(foodExpirationDate);
        foodDonation.setTitle(title);
        foodDonation.setCategory(category);
        foodDonation.setCity(city);
        foodDonation.setStatus(state);
        return foodDonation;
    }

    public FoodDonation insert(FoodUnit unit, Float amount,
                               OffsetDateTime foodExpirationDate, String title,
                               DonationCategory category, String description, City city,
                               DonationStatus state) {
        FoodDonation foodDonation = create(unit, amount, foodExpirationDate, title,
                        category, description, city, state);
        return foodDonationRepository.save(foodDonation);
    }

    public FoodDonation get(Long id) {
        return foodDonationRepository.findById(id).orElse(null);
    }
}
