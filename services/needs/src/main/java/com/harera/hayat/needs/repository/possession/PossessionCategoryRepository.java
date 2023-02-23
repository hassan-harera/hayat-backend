package com.harera.hayat.needs.repository.possession;

import com.harera.hayat.needs.model.possession.PossessionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PossessionCategoryRepository extends JpaRepository<PossessionCategory, Long> {
}