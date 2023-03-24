package com.harera.hayat.framework.repository.clothing;

import com.harera.hayat.framework.model.ClothingSeason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothingConditionRepository extends JpaRepository<ClothingSeason, Long> {
}
