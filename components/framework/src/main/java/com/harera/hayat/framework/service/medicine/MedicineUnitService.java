package com.harera.hayat.framework.service.medicine;

import com.harera.hayat.framework.model.medicine.MedicineUnit;
import com.harera.hayat.framework.model.medicine.MedicineUnitDto;
import com.harera.hayat.framework.repository.repository.MedicineUnitRepository;
import com.harera.hayat.framework.util.ObjectMapperUtils;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineUnitService {

    private final MedicineUnitRepository medicineUnitRepository;
    private final ModelMapper modelMapper;

    public MedicineUnitService(MedicineUnitRepository medicineUnitRepository,
                    ModelMapper modelMapper) {
        this.medicineUnitRepository = medicineUnitRepository;
        this.modelMapper = modelMapper;
    }

    @Cacheable("medicine_units")
    public List<MedicineUnitDto> list() {
        List<MedicineUnit> medicineUnitList = medicineUnitRepository.findAll();
        return ObjectMapperUtils.mapAll(medicineUnitList, MedicineUnitDto.class);
    }

    @Cacheable("medicine_units#id")
    public MedicineUnitDto get(long id) {
        MedicineUnit medicineUnit = medicineUnitRepository.findById(id).orElseThrow();
        return modelMapper.map(medicineUnit, MedicineUnitDto.class);
    }
}
