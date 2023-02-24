package com.harera.hayat.needs.repository.possession;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harera.hayat.needs.model.possession.PossessionCondition;

public interface PossessionConditionRepository extends JpaRepository<PossessionCondition, Long> {
}