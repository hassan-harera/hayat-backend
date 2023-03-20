package com.harera.hayat.donations.stubs.medicine;

import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.DonationStatus;
import com.harera.hayat.donations.model.medicine.MedicineDonation;
import com.harera.hayat.donations.repository.medicine.MedicineDonationRepository;
import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.model.medicine.MedicineUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MedicineDonationStubs {

    private final MedicineDonationRepository medicineDonationRepository;

    public MedicineDonation create(MedicineUnit unit, Float amount,
                                   OffsetDateTime medicineExpirationDate, String title,
                                   DonationCategory category, String description, City city,
                                   DonationStatus state) {
        MedicineDonation medicineDonation = new MedicineDonation();
        medicineDonation.setId(0L);
        medicineDonation.setMedicineUnit(unit);
        medicineDonation.setQuantity(amount);
        medicineDonation.setMedicineExpirationDate(medicineExpirationDate);
        medicineDonation.setTitle(title);
        medicineDonation.setCategory(category);
        medicineDonation.setCity(city);
        medicineDonation.setStatus(state);
        return medicineDonation;
    }

    public MedicineDonation insert(MedicineUnit unit, Float amount,
                               OffsetDateTime medicineExpirationDate, String title,
                               DonationCategory category, String description, City city,
                               DonationStatus state) {
        MedicineDonation medicineDonation = create(unit, amount, medicineExpirationDate, title,
                        category, description, city, state);
        return medicineDonationRepository.save(medicineDonation);
    }

    public MedicineDonation get(Long id) {
        return medicineDonationRepository.findById(id).orElse(null);
    }
}
