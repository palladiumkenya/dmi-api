package com.kenyahmis.dmiapi.repository;

import com.kenyahmis.dmiapi.model.Case;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CaseRepository extends JpaRepository<Case, UUID> {
    Optional<Case> findByVisitUniqueIdAndMflCode(String visitId, String mflCode);
    List<Case> findByBatchId(UUID batchId);
    Integer countByBatchId(UUID batchId);
}
