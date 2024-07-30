package com.kenyahmis.dmiapi.repository;

import com.kenyahmis.dmiapi.model.FlaggedCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlaggedConditionRepository extends JpaRepository<FlaggedCondition, UUID> {}
