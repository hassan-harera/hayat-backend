package com.harera.hayat.donations.stubs.medicine;

import com.harera.hayat.framework.model.medicine.Medicine;
import com.harera.hayat.framework.model.medicine.MedicineUnit;
import com.harera.hayat.framework.repository.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MedicineStubs {

    @Autowired
    private MedicineRepository medicineUnitRepository;

    public Medicine insert(String arabicName, String englishName, MedicineUnit unit) {
        Medicine medicineUnit = create(arabicName, englishName);
        medicineUnit.setUnit(unit);
        return medicineUnitRepository.save(medicineUnit);
    }

    private Medicine create(String arabicName, String englishName) {
        Medicine medicineUnit = new Medicine();
        medicineUnit.setArabicName(arabicName);
        medicineUnit.setEnglishName(englishName);
        return medicineUnit;
    }
}
