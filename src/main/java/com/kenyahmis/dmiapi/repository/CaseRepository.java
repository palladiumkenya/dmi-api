package com.kenyahmis.dmiapi.repository;

import com.kenyahmis.dmiapi.model.IllnessCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CaseRepository extends JpaRepository<IllnessCase, UUID> {
    Optional<IllnessCase> findByVisitUniqueIdAndMflCode(String visitId, String mflCode);
    List<IllnessCase> findByBatchId(UUID batchId);
    Page<IllnessCase> findAllByLoadDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Integer countByBatchId(UUID batchId);
}
