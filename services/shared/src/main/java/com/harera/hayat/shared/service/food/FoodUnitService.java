package com.harera.hayat.shared.service.food;

import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.model.food.FoodUnit;
import com.harera.hayat.framework.model.food.FoodUnitResponse;
import com.harera.hayat.framework.repository.food.FoodUnitRepository;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class FoodUnitService {

    private final FoodUnitRepository foodUnitRepository;
    private final ModelMapper modelMapper;

    public FoodUnitService(FoodUnitRepository foodUnitRepository, ModelMapper modelMapper) {
        this.foodUnitRepository = foodUnitRepository;
        this.modelMapper = modelMapper;
    }

    @Cacheable("food_units#id")
    public FoodUnitResponse get(Long id) {
        FoodUnit foodUnit = foodUnitRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(FoodUnit.class, id));
        return modelMapper.map(foodUnit, FoodUnitResponse.class);
    }

    @Cacheable("food_units")
    public List<FoodUnitResponse> list() {
        List<FoodUnit> foodUnitList = foodUnitRepository.findAll();
        List<FoodUnitResponse> list = new LinkedList<>();
        for (FoodUnit foodUnit : foodUnitList) {
            list.add(modelMapper.map(foodUnit, FoodUnitResponse.class));
        }
        return list;
    }
}
