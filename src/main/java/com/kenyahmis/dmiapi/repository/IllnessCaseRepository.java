package com.kenyahmis.dmiapi.repository;

import com.kenyahmis.dmiapi.model.RespiratoryIllnessCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IllnessCaseRepository extends JpaRepository<RespiratoryIllnessCase, UUID> {
    Optional<RespiratoryIllnessCase> findByVisitUniqueIdAndMflCode(String visitId, String mflCode);
    List<RespiratoryIllnessCase> findByBatchId(UUID batchId);
    Integer countByBatchId(UUID batchId);
}
