package com.harera.hayat.framework.service.medicine;

import com.harera.hayat.framework.model.medicine.MedicineUnit;
import com.harera.hayat.framework.repository.repository.MedicineUnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineUnitService {

    private final MedicineUnitRepository medicineUnitRepository;

    public MedicineUnitService(MedicineUnitRepository medicineUnitRepository) {
        this.medicineUnitRepository = medicineUnitRepository;
    }

    public List<MedicineUnit> list() {
        return medicineUnitRepository.findAll();
    }

    public MedicineUnit get(long id) {
        return medicineUnitRepository.findById(id).orElseThrow();
    }
}
