package com.kenyahmis.dmiapi.repository;

import com.kenyahmis.dmiapi.model.Complaint;
import com.kenyahmis.dmiapi.model.RiskFactor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RiskFactorRepository extends JpaRepository<RiskFactor, UUID> {

    Optional<RiskFactor> findByCaseIdAndRiskFactorId(UUID caseId, String riskFactorId);
}

