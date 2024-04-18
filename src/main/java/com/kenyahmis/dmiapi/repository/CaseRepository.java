package com.kenyahmis.dmiapi.repository;

import com.kenyahmis.dmiapi.model.IllnessCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CaseRepository extends JpaRepository<IllnessCase, UUID> {
    Optional<IllnessCase> findByVisitUniqueIdAndMflCode(String visitId, String mflCode);
    List<IllnessCase> findByBatchId(UUID batchId);
    Integer countByBatchId(UUID batchId);
}
