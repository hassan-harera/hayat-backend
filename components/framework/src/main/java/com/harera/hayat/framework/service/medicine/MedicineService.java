package com.harera.hayat.framework.service.medicine;

import com.harera.hayat.framework.model.medicine.Medicine;
import com.harera.hayat.framework.repository.repository.MedicineRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {

    private final MedicineRepository medicineRepository;

    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public List<Medicine> list(int page) {
        page = Integer.max(page, 1) - 1;
        return medicineRepository.findAll(Pageable.ofSize(10).withPage(page)).getContent();
    }

    public Medicine get(long id) {
        return medicineRepository.findById(id).orElseThrow();
    }

    public List<Medicine> search(String query, int page) {
        page = Integer.max(page, 1) - 1;
        return medicineRepository.search(query, Pageable.ofSize(10).withPage(page));
    }
}
