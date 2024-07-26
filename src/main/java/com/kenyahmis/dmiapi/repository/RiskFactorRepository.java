package com.kenyahmis.dmiapi.repository;

import com.kenyahmis.dmiapi.model.RiskFactor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RiskFactorRepository extends JpaRepository<RiskFactor, UUID> {}

