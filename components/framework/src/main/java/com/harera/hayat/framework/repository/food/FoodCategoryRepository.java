package com.harera.hayat.framework.repository.food;

import com.harera.hayat.framework.model.food.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {
}
