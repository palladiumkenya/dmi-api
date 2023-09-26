package com.kenyahmis.dmiapi.repository;

import com.kenyahmis.dmiapi.model.Lab;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LabRepository extends JpaRepository<Lab, UUID> {

    Optional<Lab> findByCaseIdAndOrderId(UUID caseId, String orderId);
}
