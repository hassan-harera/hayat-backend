package com.harera.hayat.framework.repository.clothing;

import com.harera.hayat.framework.model.clothing.ClothingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothingCategoryRepository
                extends JpaRepository<ClothingCategory, Long> {
}
