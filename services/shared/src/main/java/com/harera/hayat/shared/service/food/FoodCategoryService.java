package com.harera.hayat.shared.service.food;

import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.model.food.FoodCategory;
import com.harera.hayat.framework.model.food.FoodCategoryResponse;
import com.harera.hayat.framework.model.food.FoodUnit;
import com.harera.hayat.framework.repository.food.FoodCategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;
    private final ModelMapper modelMapper;

    public FoodCategoryService(FoodCategoryRepository foodCategoryRepository,
                    ModelMapper modelMapper) {
        this.foodCategoryRepository = foodCategoryRepository;
        this.modelMapper = modelMapper;
    }

    @Cacheable("food_categories#id")
    public FoodCategoryResponse get(Long id) {
        FoodCategory category = foodCategoryRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(FoodUnit.class, id));
        return modelMapper.map(category, FoodCategoryResponse.class);
    }

    @Cacheable("food_categories")
    public List<FoodCategoryResponse> list() {
        List<FoodCategory> foodUnitList = foodCategoryRepository.findAll();
        List<FoodCategoryResponse> list = new LinkedList<>();
        for (FoodCategory foodUnit : foodUnitList) {
            list.add(modelMapper.map(foodUnit, FoodCategoryResponse.class));
        }
        return list;
    }
}
